import numpy as np
from sklearn.base import  BaseEstimator, ClassifierMixin

class DiscreteNBC (BaseEstimator, ClassifierMixin):

    def __init__(self, domain_sizes = None,laplace=False):
        self.class_labels_ = None # etykiety klas, oblcizneia na 1,2,3 a pozniej zmapowac do peirwotnych
        self.PY_ = None # prawdopodbienstwa klas apriori (prawd. wystapienia danej klasy), wektor numpy z prawdopodbienstwami klas, czyli jak czesto jest jedna,druga... klasa
        self.P_ = None # struktura 3-wymairowa z wszystkimi rozkladami warunkowymi =>
        # P[2,7,1] lub P[2,7][1] = pr(X_7 = 1 | Y = 2)  "prawodpodbienstwo ze zmienna nr 7  o wartosci 1 przyjmuje klase nr 2",
        #  na zew tabela obiektow, a 3 wymair roznej dlugosci rozklady warunkowe dla konrketnej zmiennej pod warunkiem konrketnej klasy
        #  czyli prawdopodbienstwo ze dla wina klasy 2  smolistosc (zmienna x 7) jest rowna 1 [2,7][1]
        self.domains_sizes_ = domain_sizes #rozmiar dziedziny, przygotowanie na brak wartosic z jakiejs szuflady, wektor z dziediznami danej cechy
        self.laplace_ = laplace #poprawka dla malych danych cuzacych poprawia skutecznosc, bo nie uistawia 0 wiec nie sa pomijane w oblcizeniach

        pass

    def fit(self,X,y):
        m, n = X.shape # m wierszy, n kolumn
        self.class_labels_ = np.unique(y) #zapamietanie etykiet klas np., czlowiek rasy bialy czarny
        y_normalized = np.zeros(m, dtype="int8") # ektor dla znormalizownaych etykiet

        for yy,label in enumerate(self.class_labels_) : #etykietki
            y_normalized[y == label] = yy # y == label => zwraca true tam gdzie rowne label, pozniej tam wstawiamy yy

        self.PY_ = np.zeros(self.class_labels_.size)

        for yy,label in enumerate(self.class_labels_) : #ile jest wystapien danej klasy
            self.PY_[yy] = np.sum(y == label) / m #liczba wystapien klasy o etykietce label , dizelimy przez m(liczba danych testowych) zeby [0,1]
        #print(self.PY_)

        self.P_ = np.empty((self.class_labels_.size , n), dtype="object") #rozkaldy warunkowe, ( liczba klas,ile kolumn - parametrow)
        # klasa zmienna wartosc
        for yy,label in enumerate(self.class_labels_) : # po klasach  czyli kolumnach paprametrach
            for j in range(n): #licnzik po  zakresie ile jest kolumn
                self.P_[yy,j] = np.zeros(self.domains_sizes_[j]) #incijalziacja, zalokwoanie wektora dla danej domeny

        #skan danych
        for i in range(m): #petla po przykladach uczacych
            for j in range(n): #petla po kolumnach
                v = X[i,j] #zmienna x(n) przyjela wartosc v
                self.P_[y_normalized[i], j][v] += 1  # y_normalized[i] rozpoznaje jakiej klasy jest dana probka, no i dla tej klasy , o zmiennej j zwiekszamy licznik dla wartosci(kubelka) v

        #zamaina licznosci w prawdopodbienstwa
        if not self.laplace_:
            for yy,label in enumerate(self.class_labels_):
                y_sum = self.PY_[yy] * m #liczba przykladow klasy y
                for j in range(n):
                    self.P_[yy,j] /= y_sum
        else:
            for yy,label in enumerate(self.class_labels_):
                y_sum = self.PY_[yy] * m #liczba przykladow klasy y
                for j in range(n):
                    self.P_[yy,j] = (self.P_[yy,j] + 1) / (y_sum + self.domains_sizes_[j])
        #print(self.P_)

        pass

    def predict(self,X): #zwraca dla kazdego (wiersza)x w X etyikety klasy
        return self.class_labels_[np.argmax(self.predict_proba(X),axis=1)]

        pass

    def predict_proba(self,X): #zwraca dla kazdego x w X wektor prawdopdobienstw: P(Y = 1| x), P(Y = 2 | x) ,P(Y=3 | x)
        # wzor ->   y* = arg max_y prod{j = 1}^n P(X_j - x+j | Y = y) P(Y = y)
        m,n = X.shape
        scores = np.ones((m, self.class_labels_.size))  # kolumn tyle co klas
        for i in range(m):
            for yy in range( self.class_labels_.size):
                for j in range(n): # po kolumnach
                    scores[i,yy] *= self.P_[yy,j][ X[i,j]]
            s = scores[i].sum()
            if s > 0.0:
                scores[i] /= s

        #scores = np.divide(scores,np.sum(scores,axis=0),axis=1)
        #print(scores)
        return scores
