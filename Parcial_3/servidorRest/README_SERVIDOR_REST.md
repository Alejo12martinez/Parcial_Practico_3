
Parcial - Parte 2 - servidorRest

1.Levantar la Máquina Virtual:
vagrant up


2. Iniciar Sesión SSH en la Máquina Virtual:
vagrant ssh servidorRest


3. Actualizar los Repositorios:
sudo apt-get update

4. Cambiar al Directorio del Proyecto:
cd /vagrant


5. Instalar Dependencias de Flask:
pip install --upgrade Flask Flask-MySQLdb


6. Crear y Activar un Entorno Virtual:
sudo mkdir /opt/venv
sudo apt-get install python3.10-venv
sudo python3 -m venv /opt/venv
source /opt/venv/bin/activate


7. Instalar Dependencias Adicionales con pip:
sudo pip install Flask Flask-MySQLdb Jinja2 MarkupSafe Werkzeug blinker click itsdangerous mysqlclient


8. Configurar Variables de Entorno y Ejecutar la Aplicación:
export FLASK_APP=apirest_mysql.py

cd /vagrant
source /opt/venv/bin/activate
export FLASK_APP=apirest_mysql.py
python3 -m flask run --host=0.0.0.0


9. Pruebas desde postman
Petición GET
http://192.168.60.3:5000/books/2