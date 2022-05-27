<?php

$arrContextOptions=array("ssl"=>array( "verify_peer"=>false,"verify_peer_name"=>false,),);

$html = file_get_contents('https://pareekshabhavan.uoc.ac.in/index.php/examination/notifications',false,stream_context_create($arrContextOptions));

$start = explode('<div id="PG" class="tabcontent">',$html);

$end = explode('<div id="OTHER" class="tabcontent">',$start[1]);

$movies = array();

preg_match_all("!<li class='notif'><a.*?href=.*?>([\s\S]*?)<font.*?<\/li>!",$end[0],$match);
$movies['name']= $match[1];

preg_match_all("!<li class='notif'><a.*?href='(.*?)'>[\s\S]*?<font.*?<\/li>!",$end[0],$match);
$movies['link']= $match[1];

preg_match_all("!<li class='notif'><a.*?href=.*?>[\s\S]*?<font.*?>\[(.*?)\].*?\/li>!",$end[0],$match);
$movies['date']= $match[1];

for($i=0;$i<count($movies['date']);$i++)
{
     $movies['date'][$i] = str_replace(" ","-",$movies['date'][$i]);
}

$items = array();

for($i=0;$i<count($movies['name']);$i++)
{
	$temp = [
				'title' => iconv("UTF-8","UTF-8//IGNORE",$movies['name'][$i]),
				'link' => iconv("UTF-8","UTF-8//IGNORE",$movies['link'][$i]),
				'date' => iconv("UTF-8","UTF-8//IGNORE",$movies['date'][$i])
			];
			
   array_push($items,$temp);
}


echo json_encode($items);


  ?>
