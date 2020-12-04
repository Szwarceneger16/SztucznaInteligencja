import numpy as np
import Bayes.nbc as DiscreteNBC

np.set_printoptions(suppress=True,precision=9)

def import_data(path):
    wineData = np.loadtxt(path ,delimiter=",");
    y = wineData[:,0]
    X = wineData[:,1:]
    return X,y;

def discretize(X, B,discretizedYet= None, X_ref = None): # X - dane do dyskretyzacji, B - lcizba kubelkow, X_ref - min/max wzgledem tego
    if ( X_ref is None):
        X_ref = X
    X_copy = np.delete(X, discretizedYet, 1)
    X_ref = np.delete(X_ref, discretizedYet, 1)
    mins = np.min(X_ref, axis=0) # minima (13) kolumn , czyli na rzecz kolumna, brana po wierszu -> 13 minimow dla poszczegolnych kolumn
    maxes = np.max(X_ref,axis=0) # 13 maximow dla psozczegolnych kolumn
    X_d = np.floor((X_copy - mins) / (maxes - mins) * B).astype("int8")  #wzor na dyskretyzacje, max szuflad 127
    X_d = np.clip(X_d, 0, B - 1)  # obcinanie elementow wystajacych poza przedzial (koszyki)
    return np.insert(X_d, discretizedYet, X[:,discretizedYet], axis=1)


def train_test_split( X, y, train_ratio= 0.75,seed= 0):
    m = X.shape[0]
    np.random.seed(seed)
    indexes = np.random.permutation(m)
    X = X[indexes]
    y = y[indexes]
    split = round(train_ratio * m)
    X_train = X[:split]     # 0:split
    y_train = y[:split].astype("int32")
    X_test = X[split:]
    y_test = y[split:].astype("int32")


    return X_train, y_train, X_test,y_test

def myMain():
    # http://archive.ics.uci.edu/ml/datasets/Mushroom
    main_path = 'C:\\Users\\GSzwa\\IdeaProjects\\Si\\Python\\Bayes\\'
    X,y = import_data(main_path+"wineSet\\"+"wine.data")

    X_train, y_train, X_test, y_test = train_test_split(X,y,train_ratio=0.75,seed=0)
    m,n = X_train.shape
    B = 2
    X_train_d = discretize(X_train,B) #dyskretyzacja danych testowych
    X_test_d = discretize(X_test,B,X_ref=X_train) #dyskretyzacja danych z przycieciem od testowych
    # print(X_train_d)
    # print(X_test_d)
    domain_sizes = np.ones(n,dtype="int8")*B #wektor z rozmairami koszykow dla danych cech, akzda dziedzina ma B wartosci
    dnbc = DiscreteNBC(domain_sizes= domain_sizes, laplace= True)
    dnbc.fit(X_train_d,y_train) #uczymy

    #na rzecz danych uczacych
    predictions = dnbc.predict(X_train_d)
    print("TRAIN ACC: "+ str((predictions == y_train).mean()))
    print("TRAIN ACC2: " + str(dnbc.score(X_train_d,y_train)))

    # na rzecz danych testowych
    predictions = dnbc.predict(X_test_d)
    print("TEST ACC: "+ str((predictions == y_test).mean()))
    print("TEST ACC2: " + str(dnbc.score(X_test_d, y_test)))




    return;


if __name__ == '__main__':
    myMain()
