import numpy as np
from matplotlib import pyplot as plt
from Perceptron.SimplePerceptron import SimplePerceptron

np.set_printoptions(suppress=True,precision=9)
main_path = 'C:\\Users\\GSzwa\\IdeaProjects\\Si\\Python\\Perceptron\\'

def myMain():
    #np.random.seed(0)
    m = 1000
    m_half = int(m/2)
    # X1 = np.random.rand(m,1)
    # X2u = np.random.rand(m_half,1) * 0.45 + 0.55
    # X2b = np.random.rand(m_half,1) * 0.45
    # X = np.c_[X1,np.r_[X2u,X2b]]
    #y = np.r_[np.ones(m_half),-np.ones(m_half)]

    X1 = np.random.rand(m,1) * 2 * np.pi
    X2 = (np.random.rand(m,1) * 2) -1
    X = np.c_[X1,X2]

    y = []
    for xx in X:
        y.append(-1) if np.abs( np.sin(xx[0])) > np.abs(np.sin(xx[1])) else y.append(1)
    X = (X - np.min(X, axis=0)) / (np.max(X, axis=0) - np.min(X, axis=0))

    plt.scatter(X[:,0],X[:,1],c=y,s=2)
    plt.show()

    clf = SimplePerceptron(eta=1.0,mp=30)
    w,k = clf.fit(X,y,1000)
    print(w,k)
    # x1 = np.array([0,1])
    # x2 = -(w[0] + w[1] * x1) / w[2]
    # plt.plot(x1,x2)
    # plt.show()

    lin_xx, lin_yy = (np.linspace(np.min(X[:,0]), np.max(X[:,0])), np.linspace(np.min(X[:,1]), np.max(X[:,1])))
    temp = np.zeros((np.shape(lin_xx)[0], np.shape(lin_xx)[0]))
    xs, ys = np.meshgrid(lin_xx, lin_yy)
    # j = 0
    # i = 0
    # for a in xs:
    #     for b in ys:
    #         temp[i][j] = clf.predict( np.asarray([[a[j], b[i]]]) )
    #         i = i + 1
    #     i = 0
    #     j = j + 1
    for ai,a in enumerate(lin_yy):
        for bi,b in enumerate(lin_xx):
            temp[ai][bi] = clf.predict( np.asarray([[a, b]]) )
    plt.contour(xs, ys, temp)
    plt.show()

    print("TRAIN ACC:" + str(clf.score(X,y)))
    pass


if __name__ == '__main__':
    myMain()