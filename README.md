# RGWS-PwA
Continuación del proyecto RES-PwA. https://github.com/juanseleon1/RES-PwA
La enfermedad de Alzheimer es un trastorno neurológico progresivo. Esta es la causa más común de demencia, un deterioro continuo en el pensamiento, el comportamiento y las habilidades sociales, que afecta la capacidad de una persona para vivir de forma independiente. Se ha demostrado que a medida que transcurre el tiempo, los pacientes de Alzheimer aumentan exponencialmente a un ritmo mayor con respecto a la cantidad de cuidadores clasificados para poder tratarlos. Esto implica que a futuro se generará una atención de mala calidad para los pacientes y por consiguiente no se podrá tratar de una buena forma el deterioro mental y físico de los pacientes. 

Con el transcurso de los años se ha demostrado que la actividad física en los pacientes con la enfermedad es una buena práctica para su recuperación y tratamiento. Debido a esto se desarrolló el sistema RGWS-PWA (Robot Guided Workout Support for People With Dementia), cuyo objetivo es brindar una serie de rutinas físicas para adultos mayores que padecen la enfermedad de Alzheimer en sus primeras etapas. El sistema está basado en la arquitectura BDI-CHA propuesta por el grupo SIRP de la Pontificia Universidad Javeriana en conjunto con el modelo arquitectónico del Robot Pepper de SoftBank Robotics que se integró en el sistema RGWS-PWA.  RGWS-PWA posee la capacidad de poder realizar rutinas de ejercicios especializadas para adultos mayores convirtiéndose en un asistente útil para los cuidadores de los pacientes con la enfermedad. El trabajo se realizó en conjunto con especialistas en el área de medicina, psicología y gerontología.

Estructura del Repositorio:
* Android: Implementacioó inicial del modulo intermediario a partir de QiSDK para el SO Android.
* BESA: Implementación del modulo RGWS-PwA Agent, a partir de la extensión del framework BESA-BDI
* Python: Implementación del modulo Python Utilities, a partir de la SDK qi para Python.
* Topicos: Archivos .top con los topicos basados en ALDialog.
* Postgres: Contiene scripts de la base de datos Postgres.
