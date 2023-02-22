 

 
 

rm -r GymFrontEnd/
mkdir /var/www/GymFrontEnd/
chown -R $USER:$USER /var/www/GymFrontEnd/
cd /home/ubuntu/project
cp -r build/* /var/www/GymFrontEnd/
cd /var/www/GymFrontEnd/

