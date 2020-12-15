import numpy as np
from Bayes.nbc import DiscreteNBC as DiscreteNBC

np.set_printoptions(suppress=True,precision=9)
main_path = 'C:\\Users\\GSzwa\\IdeaProjects\\Si\\Python\\Bayes\\'

def import_data(path):
    Data = None
    try:
        Data = np.loadtxt(path ,delimiter=",",dtype="int");
    except:
        try:
            Data = np.loadtxt(path, delimiter=",", dtype="str");
        except:
            return None
    y = Data[:,0]
    X = Data[:,1:]
    return X,y;

def discretize(X, B, X_ref=None):
    if X_ref is None:
        X_ref = X

    minimum = np.min(X_ref, axis=0)
    maximum = np.max(X_ref, axis=0)

    X_d = np.floor((X - minimum) / (maximum - minimum) * B).astype('int32')

    return np.clip(X_d, 0, B - 1)

def discretize2(X, B,discretizedYet= [], X_ref = None): # X - dane do dyskretyzacji, B - lcizba kubelkow, X_ref - min/max wzgledem tego
    if ( X_ref is None):
        X_ref = X
    if type(discretizedYet) != "<class 'numpy.ndarray'>":
        discretizedYet = np.array(discretizedYet,dtype=int)
    X_copy = np.delete(X, discretizedYet, axis=1)
    X_ref = np.delete(X_ref, discretizedYet, axis=1)
    if (X_copy.size == 0):
        return None
    mins = np.min(X_ref, axis=0) # minima (13) kolumn , czyli na rzecz kolumna, brana po wierszu -> 13 minimow dla poszczegolnych kolumn
    maxes = np.max(X_ref,axis=0) # 13 maximow dla psozczegolnych kolumn
    X_d = np.floor((X_copy - mins) / (maxes - mins) * B).astype("int8")  #wzor na dyskretyzacje, max szuflad 127
    X_d = np.clip(X_d, 0, B - 1)  # obcinanie elementow wystajacych poza przedzial (koszyki)
    for i in enumerate(X_copy):
        if i in discretizedYet:
            unqLabels, inverted = np.unique(X[i],return_inverse=True)
            X[i] = inverted.astype("int")
    X = X.astype("int")
    return np.insert(X_d, discretizedYet, X[:,discretizedYet], axis=1)

def forDiscretizeiedYet(X):
    domains = []
    for i,_ in enumerate(X[0]):
        unqLabels, inverted = np.unique(X[:,i],return_inverse=True)
        X[:,i] = inverted.astype("int")
        domains.append(unqLabels.size)
    return X.astype("int") , np.array(domains).astype("int")


def train_test_split( X, y, train_ratio= 0.75,seed= 0):
    m = X.shape[0]
    np.random.seed(seed)
    indexes = np.random.permutation(m)
    X = X[indexes]
    y = y[indexes]
    split = round(train_ratio * m)
    X_train = X[:split]     # 0:split
    y_train = y[:split]#.astype("int32",casting="safe")
    X_test = X[split:]
    y_test = y[split:]#.astype("int32",casting="safe")


    return X_train, y_train, X_test,y_test

def wineMain():
    X,y = import_data(main_path+"wineSet\\wine.data")

    X_train, y_train, X_test, y_test = train_test_split(X,y,train_ratio=0.75,seed=0)
    m,n = X_train.shape
    B = 5
    X_train_d = discretize2(X_train,B) #dyskretyzacja danych testowych
    X_test_d = discretize2(X_test,B,X_ref=X_train) #dyskretyzacja danych z przycieciem od testowych
    # print(X_train_d)
    # print(X_test_d)
    domain_sizes = np.ones(n,dtype="int8")*B #wektor z rozmairami koszykow dla danych cech, akzda dziedzina ma B wartosci
    dnbc = DiscreteNBC(domain_sizes= domain_sizes, laplace= False,useLogarithm=False)
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

def mushroomMain():
    # http://archive.ics.uci.edu/ml/datasets/Mushroom
    X, y = import_data(main_path + "mushroomSet\\agaricus-lepiota.data")
    X, domains = forDiscretizeiedYet(X)  # zamiana na numerki stringow

    X = np.tile(X,(1,100))
    domains = np.tile(domains, 100)

    X_train, y_train, X_test, y_test = train_test_split(X,y,train_ratio=0.1,seed=0)
    m,n = X_train.shape

    dnbc = DiscreteNBC(domain_sizes=domains, laplace=True,useLogarithm=True)
    dnbc.fit(X_train, y_train)  # uczymy

    # na rzecz danych uczacych
    predictions = dnbc.predict(X_train)
    print("TRAIN ACC: " + str((predictions == y_train).mean()))
    print("TRAIN ACC2: " + str(dnbc.score(X_train, y_train)))

    # na rzecz danych testowych
    predictions = dnbc.predict(X_test)
    print("TEST ACC: " + str((predictions == y_test).mean()))
    print("TEST ACC2: " + str(dnbc.score(X_test, y_test)))

    pass


if __name__ == '__main__':
    mushroomMain()
