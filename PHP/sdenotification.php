<?php

$url  = 'http://sdeuoc.ac.in/?q=content/notifications';
$ch = curl_init();

try
{
curl_setopt($ch, CURLOPT_URL,$url);
curl_setopt($ch, CURLOPT_HEADER,0);
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER,false);
curl_setopt($ch, CURLOPT_RETURNTRANSFER,1);

$result = curl_exec($ch);


$posts = array();

preg_match_all('!<strong><a href=".*?">(.*?)<\/a>.*?<\/strong>!',$result,$match);
$posts['title']=$match[1];
preg_match_all('!<strong><a href="(.*?)">.*?<\/a>.*?<\/strong>!',$result,$match);
$posts['link']=$match[1];


$items = array();

for($i=0;$i<count($posts['title']);$i++)
{
	$temp = [
				'title' => iconv("UTF-8","UTF-8//IGNORE",$posts['title'][$i]),
				'link' => iconv("UTF-8","UTF-8//IGNORE",$posts['link'][$i])
			];
			
   array_push($items,$temp);
}

echo json_encode($items);


}
catch(Exception $e)
{
    echo 'Message: ' .$e->getMessage();
}

curl_close($ch);

?>