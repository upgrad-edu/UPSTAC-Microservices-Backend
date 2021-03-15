
cd /home/ec2-user/deploy/upstac-api
FILE=$HOME/upstacApp.pid
echo $FILE
if test -f "$FILE"; then
  pid=`cat $FILE`
  EXIT_CODE=0
  kill $pid || EXIT_CODE=$?
  echo "Stopped Server , Process exited with "$EXIT_CODE
fi





version="0.0.1-SNAPSHOT"
filename='upstac-api-'$version'.jar'

rm -rf upstac-api-*.jar
cp delivery/$filename $filename
nohup java -jar $filename >app.log 2>&1 &
read -p "Waiting for Api server to be started" -t 30
cat app.log
exit 0