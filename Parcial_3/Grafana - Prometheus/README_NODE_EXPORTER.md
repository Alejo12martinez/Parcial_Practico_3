
Paso 1 -
sudo groupadd --system node_exporter
sudo useradd -s /sbin/nologin  --system -g node_exporter node_exporter

Paso 2 - 
wget https://github.com/prometheus/node_exporter/releases/download/v1.6.1/node_exporter-1.6.1.linux-amd64.tar.gz

Paso 3 - 
tar xvfz node_exporter-*.*-amd64.tar.gz


Paso 4 - 
cd node_exporter-*.*-amd64

Paso 5 - 
sudo mv node_exporter /usr/local/bin
sudo chown node_exporter:node_exporter /usr/local/bin/node_exporter

Paso 6 - 
sudo vim /etc/systemd/system/node_exporter.service

Paso 7 - 
copiar esto en el node_exporter.service

[Unit]
Description=Node Exporter
After=network-online.target
Wants=network-online.target

[Service]
User=node_exporter
Group=node_exporter
Type=simple
ExecStart=/usr/local/bin/node_exporter

[Install]
WantedBy=multi-user.target



Paso 8 - 
sudo systemctl daemon-reload
sudo systemctl enable node_exporter.service
sudo systemctl start node_exporter.service
sudo systemctl status node_exporter.service


Paso 9 - 
http://192.168.50.4:9100/metrics
curl http://localhost:9100/metrics | grep "node_"