# Red Neuronal

La clase NeuralNetwork permite crear una red neuronal y añadirle las capas que se quiera.

Para crear un objeto de este tipo se debe especificar en el constructor el numero de inputs que va a tener la red.

Una vez creada la red se puede añadir una capa al final de la red mediante el metodo addLayer(int nodes)

La red se puede entrenar mediante backpropagation especificando los inputs introducidos y los outputs esperados.

Para probar la creación y entrenamiento de una red neuronal compilar y ejecutar con java el archivo Program.java

También se puede guardar la red neuronal serializada en un archivo para poder reconstruirla con el constructor indicando la ruta del archivo.
