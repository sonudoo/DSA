from sklearn import linear_model,datasets
from matplotlib import pyplot as plt
import numpy as np

iris = datasets.load_iris()

x = iris.data[:,0:2] #Only first two features so that we can visualize
y = iris.target

#y contains three categories 0,1,2. We will remove the third category

x = x[:100]
y = y[:100]

n = 100

#Generate random train, validation and test sets
random_indices = np.random.permutation(n)

x_random = []
y_random = []

for i in random_indices:
	x_random.append(x[i])
	y_random.append(y[i])

x_random = np.array(x_random)
y_random = np.array(y_random)

n_train = int(0.7*n)
n_validation = int(0.15*n)
n_test = n-n_train-n_validation

x_train = x_random[:n_train]
y_train = y_random[:n_train]

x_validation = x_random[n_train:n_train+n_validation]
y_validation = y_random[n_train:n_train+n_validation]

x_test = x_random[n_train+n_validation:]
y_test = y_random[n_train+n_validation:]

#Divide the train set to two classes

x_train_class0 = np.array([x_train[i] for i in range(n_train) if(y_train[i]==0)])
x_train_class1 = np.array([x_train[i] for i in range(n_train) if(y_train[i]==1)])

y_train_class0 = np.zeros(len(x_train_class0))
y_train_class1 = np.ones(len(x_train_class1))



#Logistic Regression

model = linear_model.LogisticRegression()
model.fit(x_train, y_train)

#Calculate the errors

mistakes = 0
for i in range(n_validation):
	if(model.predict(np.matrix(x_validation[i]))[0] != y_validation[i]): 
		mistakes+=1
print("Validation error = ", mistakes/n_validation)


mistakes = 0
for i in range(n_test):
	if(model.predict(np.matrix(x_test[i]))[0] != y_test[i]):
		mistakes+=1
print("Test error = ", mistakes/n_test)


#Data visualization. Didn't plot the decision boundary

plt.xlabel('Sepal length')
plt.ylabel('Sepal width')
plt.scatter(x_train_class0[:,0], x_train_class0[:,1], color='r', label='Class 0')
plt.scatter(x_train_class1[:,0], x_train_class1[:,1], color='b', label='Class 1')
plt.legend()
plt.show()


