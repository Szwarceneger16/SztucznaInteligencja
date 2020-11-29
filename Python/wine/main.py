import numpy as np
from wine.nbc import DiscreteNBC

np.set_printoptions(suppress=True,precision=3)

def import_data(path):
    wineData = np.loadtxt(path ,delimiter=",");
    y = wineData[:,0]
    X = wineData[:,1:]
    return X,y;

def discretize(X, B, X_ref = None): # X - dane do dyskretyzacji, B - lcizba kubelkow, X_ref - min/max wzgledem tego
    if ( X_ref is None):
        X_ref = X
    mins = np.min(X_ref, axis=0) # minima (13) kolumn , czyli na rzecz kolumna, brana po wierszu -> 13 minimow dla poszczegolnych kolumn
    maxes = np.max(X_ref,axis=0) # 13 maximow dla psozczegolnych kolumn
    X_d = np.floor((X - mins) / (maxes - mins) * B).astype("int8")  #wzor na dyskretyzacje, max szuflad 127
    return np.clip(X_d,0,B-1) # obcinanie elementow wystajacych poza przedzial (koszyki)


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
    main_path = 'C:\\Users\\GSzwa\\PycharmProjects\\Si\\wine\\'
    X,y = import_data(main_path+ "wine.data");

    X_train, y_train, X_test, y_test = train_test_split(X,y,train_ratio=0.75)
    m,n = X_train.shape
    B = 5
    X_train_d = discretize(X_train,B) #dyskretyzacja danych testowych
    X_test_d = discretize(X_test,B,X_ref=X_train) #dyskretyzacja danych z przycieciem od testowych
    # print(X_train_d)
    # print(X_test_d)
    domain_sizes = np.ones(n,dtype="int8")*B #wektor z rozmairami koszykow dla danych cech, akzda dziedzina ma B wartosci
    dnbc = DiscreteNBC(domain_sizes= domain_sizes)
    dnbc.fit(X_train_d,y_train) #uczymy



    return;


if __name__ == '__main__':
    myMain()
