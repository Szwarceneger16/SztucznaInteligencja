import numpy as np
from sklearn.base import  BaseEstimator, ClassifierMixin

class DiscreteNBC (BaseEstimator, ClassifierMixin):

    def __init__(self, domain_sizes = None):
        self.class_labels_ = None # etykiety klas, oblcizneia na 1,2,3 a pozniej zmapowac do peirwotnych
        self.PY_ = None # prawdopodbienstwa klas apriori (prawd. wystapienia danej klasy), wektor numpy z prawdopodbienstwami klas, czyli jak czesto jest jedna,druga... klasa
        self.P_ = None # struktura 3-wymairowa z wszystkimi rozkladami warunkowymi =>
        # P[2,7,1] lub P[2,7][1] = pr(X_7 = 1 | Y = 2)  "prawodpodbienstwo ze zmienna nr 7  o wartosci 1 przyjmuje klase nr 2",
        #  na zew tabela obiektow, a 3 wymair roznej dlugosci rozklady warunkowe dla konrketnej zmiennej pod warunkiem konrketnej klasy
        #  czyli prawdopodbienstwo ze dla wina klasy 2  smolistosc (zmienna x 7) jest rowna 1 [2,7][1]
        self.domains_sizes_ = domain_sizes #rozmiar dziedziny, przygotowanie na brak wartosic z jakiejs szuflady, wektor z dziediznami danej cechy


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
                self.P_[yy,j] = np.zeros(self.domains_sizes_[yy]) #incijalziacja, zalokwoanie wektora dla danej domeny

        #skan danych
        for i in range(m): #petla po przykladach uczacych
            for j in range(n): #petla po kolumnach
                v = X[i,j] #zmienna x(n) przyjela wartosc v
                self.P_[y_normalized[i], j][v] += 1  # y_normalized[i] rozpoznaje jakiej klasy jest dana probka, no i dla tej klasy , o zmiennej j zwiekszamy licznik dla wartosci(kubelka) v

        print(self.P_)

        pass

    def predict(self,X): #zwraca dla kazdego (wiersza)x w X etyikety klasy

        pass

    def predict_proba(self,X): #zwraca dla kazdego x w X wektor prawdopdobienstw: P(Y = 1| x), P(Y = 2 | x) ,P(Y=3 | x)

        pass