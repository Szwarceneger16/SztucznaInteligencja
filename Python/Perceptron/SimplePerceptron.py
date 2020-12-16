import numpy as np
from matplotlib import pyplot as plt
from matplotlib.colors import ListedColormap
from sklearn.base import  BaseEstimator, ClassifierMixin

my_colors = ListedColormap(["darkorange", "lightseagreen"])

class SimplePerceptron(BaseEstimator, ClassifierMixin):
    def __init__(self, eta=1.0,mp=30,sigma=0.5):
        self.class_labels_ = None
        self.w_ = None
        self.k_ = 0
        self.eta_ = eta
        self.sigma_ = sigma
        self.mp_ = mp
        self.centers_ = None
        pass

    def fit(self,X,y,Kmax = 5000):
        m,n = X.shape

        # normalziacja etykiet - zamiana str etykeit na liczby
        self.class_labels_ = np.unique(y)
        y_normalized = np.ones(m).astype("int8")
        y_normalized[ y == self.class_labels_[0]] = -1

        # normalziacja wspolrzednych
        #X = (X - np.min(X,axis=0))  / (np.max(X,axis=0) - np.min(X,axis=0))
        #X = (X - np.min(X, axis=0)) / (np.max(X, axis=0) - np.min(X, axis=0)) * 2 - 1

        # podniesienie wymiarowosci
        self.centers_ = np.random.rand(self.mp_,n) *2 -1
        plt.scatter(X[:, 0], X[:, 1], c=y, s=2,cmap=my_colors)
        plt.scatter(self.centers_[:,0],self.centers_[:,1],c='black',s=8)
        plt.show()
        if self.mp_ != 0:
            X_old = X
            X = np.ones((m,self.mp_+1),'double')
            for xx_i,xx in enumerate(X_old):
                for cc_i,cc in enumerate(self.centers_):
                    X[xx_i][cc_i+1] = self.gauss_function(xx,cc)
            self.w_ = np.zeros(self.mp_ + 1)
        else:
            self.w_ = np.zeros(n + 1)
            X = np.c_[np.ones((m,1)),X]

        #Percpetron
        self.k_ = 0
        while True:
            E = [] #lista punktów zle sklasyfikowanych
            E_y = [] #lista etykeit punktow zle skalsyfikwoanych
            for i in range(m):
                x = X[i]
                f = -1 if self.w_.dot(x.T) <= 0 else 1
                if f != y_normalized[i]:
                    E.append(x)
                    E_y.append(y_normalized[i])
            if len(E) == 0 or self.k_ > Kmax:
                break
            i = int(np.random.rand() * len(E))
            x = E[i]
            y = E_y[i]
            self.w_ = self.w_ + self.eta_ * y * x
            self.k_ += 1

        return  self.w_ , self.k_

    def predict(self,X ):
        return self.class_labels_[ (self.decision_function(X) > 0) * 1 ]

    def decision_function(self, X_old):
        m, n = X_old.shape
        if self.mp_ != 0:
            X = np.ones((m,self.mp_+1),'double')
            for xx_i,xx in enumerate(X_old):
                for cc_i,cc in enumerate(self.centers_):
                    X[xx_i][cc_i+1] = self.gauss_function(xx,cc)
        else:
            X = np.c_[np.ones((m,1)),X_old]
        return self.w_.dot(X.T)

    def gauss_function(self,x1,x2):
        z = 0
        for ii in range(len(x1)):
            z += np.power(x1[ii] - x2[ii], 2)
        z /= (2 * np.power(self.sigma_, 2))
        return np.exp(-z)
