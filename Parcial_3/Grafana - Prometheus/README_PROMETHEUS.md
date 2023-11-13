*****************INSTALACIÓN DE PROMETHEUS*************************

1. Instalar algunas herramientas para configuración de la red
sudo apt-get install net-tools

2. Instalar el editor Vim
sudo apt install vim

Paso 2 - Actualizar los Paquetes del Sistema
Actualizar la lista de paquetes del sistema para asegurarse de que se 
estén utilizando los paquetes más recientes.

sudo apt update


Paso 3 - Crear un Usuario del Sistema para Prometheus
Crear un usuario y un grupo del sistema llamado 
"prometheus" con privilegios limitados.

sudo groupadd --system prometheus
sudo useradd -s /sbin/nologin --system -g prometheus prometheus

Paso 4 - Crear Directorios para Prometheus
Crear directorios para los archivos de 
configuración y las bibliotecas de Prometheus.

sudo mkdir /etc/prometheus
sudo mkdir /var/lib/prometheus

Paso 5 - Descargar Prometheus y Extraer los Archivos
Descargar la última versión de Prometheus y extraer los archivos.

wget https://github.com/prometheus/prometheus/releases/download/v2.43.0/prometheus-2.43.0.linux-amd64.tar.gz
tar vxf prometheus*.tar.gz
cd prometheus*/


Configuración de Prometheus en Ubuntu 22.04:

Paso 6 - Mover los Archivos Binarios y Establecer el Propietario
Mover archivos binarios (prometheus y promtool) y cambiar el propietario.

sudo mv prometheus /usr/local/bin
sudo mv promtool /usr/local/bin
sudo chown prometheus:prometheus /usr/local/bin/prometheus
sudo chown prometheus:prometheus /usr/local/bin/promtool

Paso 7 - Mover los Archivos de Configuración y Establecer el Propietario
Mover archivos de configuración y establecer 
su propietario para que Prometheus pueda acceder a ellos.

sudo mv consoles /etc/prometheus
sudo mv console_libraries /etc/prometheus
sudo mv prometheus.yml /etc/prometheus
sudo chown prometheus:prometheus /etc/prometheus
sudo chown -R prometheus:prometheus /etc/prometheus/consoles
sudo chown -R prometheus:prometheus /etc/prometheus/console_libraries
sudo chown -R prometheus:prometheus /var/lib/prometheus


Paso 8 - El archivo prometheus.yml es el archivo principal de configuración de Prometheus. 
Incluye configuraciones para los objetivos a monitorear, la frecuencia de obtención de datos,
el procesamiento de datos y el almacenamiento. Puede configurar reglas de alerta y 
condiciones de notificación en el archivo.

sudo vim /etc/prometheus/prometheus.yml

ajustarlo de la siguiente manera


# my global config
global:
  scrape_interval: 15s # Set the scrape interval to every 15 seconds. Default is every 1 minute.
  evaluation_interval: 15s # Evaluate rules every 15 seconds. The default is every 1 minute.
  # scrape_timeout is set to the global default (10s).

# Alertmanager configuration
alerting:
  alertmanagers:
    - static_configs:
        - targets:
          # - alertmanager:9093

# Load rules once and periodically evaluate them according to the global 'evaluation_interval'.
rule_files:
  # - "first_rules.yml"
  # - "second_rules.yml"

# A scrape configuration containing exactly one endpoint to scrape:
# Here it's Prometheus itself.
scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
  - job_name: "prometheus"
    static_configs:
      - targets: ["192.168.50.3:9090"]

  # Add a new job for Node Exporter
  - job_name: "node_exporter"
    static_configs:
      - targets: ["192.168.50.4:9100"]
	  
	  

Paso 9 - Crear el Servicio Systemd de Prometheus
Ahora, debe crear un archivo de servicio systemd para Prometheus.

sudo vim /etc/systemd/system/prometheus.service


Incluya la siguiente configuración en el archivo, guárdelo y salga:


[Unit]
Description=Prometheus
Wants=network-online.target
After=network-online.target

[Service]
User=prometheus
Group=prometheus
Type=simple
ExecStart=/usr/local/bin/prometheus \
    --config.file /etc/prometheus/prometheus.yml \
    --storage.tsdb.path /var/lib/prometheus/ \
    --web.console.templates=/etc/prometheus/consoles \
    --web.console.libraries=/etc/prometheus/console_libraries

[Install]
WantedBy=multi-user.target


Paso 10 - Recargar Systemd
Debe recargar los archivos de configuración del sistema después de guardar el archivo 
prometheus.service para que los cambios se reconozcan.

sudo systemctl daemon-reload


Paso 11 - Iniciar el Servicio de Prometheus
Habilitar y iniciar el servicio de Prometheus.

sudo systemctl enable prometheus
sudo systemctl start prometheus
sudo systemctl status prometheus
sudo systemctl stop prometheus


Acceda a la interfaz web de Prometheus
Prometheus se ejecuta en el puerto 9090 de forma predeterminada, 
por lo que debe permitir el puerto 9090 en su firewall.
Hágalo usando el comando:
 
sudo ufw allow 9090/tcp

Con Prometheus ejecutándose correctamente, puede acceder a él a través de su navegador 
web usando 

http://192.168.50.4:9090