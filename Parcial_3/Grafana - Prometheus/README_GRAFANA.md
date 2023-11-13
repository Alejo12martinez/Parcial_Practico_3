
INSTALACIÓN DE GRAFANA

sudo apt-get update


Descarga el paquete de Grafana desde el sitio web oficial:
wget https://dl.grafana.com/oss/release/grafana_8.0.0_amd64.deb



corrige las dependencias:
sudo apt --fix-broken install
sudo apt-get install -y libfontconfig1



Intenta nuevamente instalar Grafana:
sudo dpkg -i grafana_8.0.0_amd64.deb


Inicia el servicio de Grafana:
sudo /bin/systemctl daemon-reload
sudo /bin/systemctl enable grafana-server
sudo /bin/systemctl start grafana-server

sudo /bin/systemctl status grafana-server



http://192.168.50.4:3000/



usuario primera vez
admin
admin

password
fepe*15!
