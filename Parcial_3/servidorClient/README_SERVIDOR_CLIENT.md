servidorClient

vagrant up servidorClient

cd /vagrant

sudo apt-get update
sudo apt-get install python3
sudo apt-get install python3-pip



pip install --upgrade Flask


sudo mkdir /opt/venv
sudo apt-get install python3.10-venv
sudo python3 -m venv /opt/venv
source /opt/venv/bin/activate



sudo pip3 install requests


sudo pip install Flask requests


python3 client.py



URL CLIENTE 
http://192.168.60.4:5001/make_request



vagrant ssh servidorClient

cd /vagrant

python3 client.py