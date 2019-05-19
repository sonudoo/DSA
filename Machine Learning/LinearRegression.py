import numpy as np
from sklearn import linear_model
from matplotlib import pyplot as plt

#Generate some random data
n = 100
x = np.linspace(0,4,n)
y = [np.sin(i)+i+np.random.random() for i in x]


#Split the data randomly to training, validation and testing sets

random_indices = np.random.permutation(n)

x_random = []
y_random = []

for i in random_indices:
	x_random.append(x[i])
	y_random.append(y[i])

n_train = int(0.7*n)
n_validation = int(0.15*n)
n_test = n - n_train - n_validation

x_train = x_random[0:n_train]
y_train = y_random[0:n_train]

x_validation = x_random[n_train:n_train+n_validation]
y_validation = y_random[n_train:n_train+n_validation]

x_test = x_random[n_train+n_validation:]
y_test = y_random[n_train+n_validation:]


#Generate the fitting line
model = linear_model.LinearRegression()

x_train = np.matrix(np.array(x_train).reshape(n_train,1))
y_train = np.matrix(np.array(y_train).reshape(n_train,1))

model.fit(x_train,y_train)


#Calculate the errors
validation_mse = np.mean((y_validation - model.predict(np.array(x_validation).reshape(n_validation,1)))**2)
test_mse = np.mean((y_test - model.predict(np.array(x_test).reshape(n_test,1)))**2)
print("Validation error = ", validation_mse)
print("Test error = ", test_mse)

#Data visualization
plt.scatter(np.array(x_train), np.array(y_train), color='k', label='Train set')
plt.scatter(np.array(x_validation),np.array(y_validation), color='r', label='Validation set')
plt.scatter(np.array(x_test), np.array(y_test), color='b', label='Test set')
plt.plot(x.reshape(n,1), model.predict(x.reshape(n,1)), color='g', label='Fitting line')
plt.xlabel('Features')
plt.ylabel('Targets')
plt.legend()
plt.grid(True, color='c')
plt.show()


