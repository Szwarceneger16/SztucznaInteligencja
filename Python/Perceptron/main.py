import numpy as np
from matplotlib import pyplot as plt
from matplotlib.colors import ListedColormap
from Perceptron.SimplePerceptron import SimplePerceptron

np.set_printoptions(suppress=True,precision=9)
my_colors = ListedColormap(["darkorange", "lightseagreen"])
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
    X = (X - np.min(X, axis=0)) / (np.max(X, axis=0) - np.min(X, axis=0)) *2 -1

    # plt.scatter(X[:,0],X[:,1],c=y,s=2)
    # plt.show()

    eta = 0.4
    mp = 80
    clf = SimplePerceptron(eta=eta,mp=mp)
    w,k = clf.fit(X,y,3500)
    acc = str(clf.score(X, y))
    print("TRAIN ACC:" + acc)

    #print(w,k)
    # x1 = np.array([0,1])
    # x2 = -(w[0] + w[1] * x1) / w[2]
    # plt.plot(x1,x2)
    # plt.show()

    lin_xx, lin_yy = (np.linspace(np.min(X[:,0])*1.1, np.max(X[:,0])*1.1,num=100), np.linspace(np.min(X[:,1])*1.1, np.max(X[:,1])*1.1,num=100))
    temp = np.zeros((np.shape(lin_xx)[0], np.shape(lin_xx)[0]))
    xs, ys = np.meshgrid(lin_xx, lin_yy)

    for ai,a in enumerate(lin_xx):
        for bi,b in enumerate(lin_yy):
            temp[ai][bi] = clf.predict( np.asarray([[a, b]]) )
    plt.title("Contour plot ACC, "+ acc + ", k=" + str(k) +", centers=" + str(mp) +", eta= " + str(eta))
    plt.scatter(X[:, 0], X[:, 1], c=y, s=8,cmap=my_colors)
    plt.contour(ys, xs, temp)
    plt.show()

    for ai,a in enumerate(lin_xx):
        for bi,b in enumerate(lin_yy):
            temp[ai][bi] = clf.decision_function( np.asarray([[a, b]]) )
    plt.title("Contour plot ACC, "+ acc + ", k=" + str(k) +", centers=" + str(mp) +", eta= " + str(eta))
    ax = plt.axes(projection='3d')
    ax.plot_surface(ys, xs, temp, cmap='viridis', edgecolor='none')
    plt.show()

    pass


if __name__ == '__main__':
    myMain()