#🌱 데일리그린 
집에 있는 시간이 늘어나고 있는 요즘, 나만의 식물로 힐링을 해보는 건 어떨까요?  
실제 반려식물을 가꾸고 돌보면서 느낄 수 있는 소소한 행복과 기쁨이 정서적 힐링감을 안겨준다고 합니다.   
그래서 저희는 하루하루 생기있는 삶을 도와줄 ‘데일리 그린'을 제작하게 되었습니다.

#👤 로그인,로그아웃 화면 
<p>
<img width="200" alt="스크린샷 2021-02-14 오후 3 45 55" src="https://user-images.githubusercontent.com/61643122/107870778-c3b2c480-6ede-11eb-9eec-14ad94461ebb.png">
<img width="200" alt="스크린샷 2021-02-14 오후 3 46 11" src="https://user-images.githubusercontent.com/61643122/107870807-02487f00-6edf-11eb-8021-e5af6f7f7353.png">
</p>
앱을 실행하면 가장 먼저 스플래시 화면이 나오고 로그인 화면으로 넘어갑니다.
email과 password는 edittext로 값을 받아옵니다. 값을 입력하지 않았을 경우 아이디/비밀번호를 입력하라는 Toast 메시지가 뜨고 회원이 아닌 경우에는 ‘아이디/비밀번호가 일치하지 않습니다'라는 
Toast 메세지가 뜨게됩니다. 로그인을 성공하면 홈 화면으로 넘어가게 됩니다.

#🏠 홈 화면 
<p>
<img width="200" alt="스크린샷 2021-02-14 오후 3 51 00" src="https://user-images.githubusercontent.com/61643122/107870864-b3e7b000-6edf-11eb-96bd-5709ae188bbc.png">
<img width="200" alt="스크린샷 2021-02-14 오후 3 48 28" src="https://user-images.githubusercontent.com/61643122/107870866-c366f900-6edf-11eb-9340-d6e2a37aee07.png">
<img width="200" alt="스크린샷 2021-02-14 오후 3 51 21" src="https://user-images.githubusercontent.com/61643122/107870871-d4b00580-6edf-11eb-9e34-9c9be4351748.png">
<img width="200" alt="스크린샷 2021-02-14 오후 3 51 43" src="https://user-images.githubusercontent.com/61643122/107870884-eb565c80-6edf-11eb-8ebe-29e81051dd84.png">
</p>
홈에서는 하단의 네비게이션 바를 통해 각각의 페이지로 이동할 수 있습니다. 액티비티에서 재사용이 가능하고 관리가 용이한 fragment를 사용하였으며, 부드럽고 자연스러운 움직임이 가능합니다. <br>
홈 화면에서는 recyclerview(리사이클러뷰)를 사용해서 나의 반려식물 리스트를 불러옵니다. 하단에 있는 동그라미 버튼을 누르면  반려식물을 추가할 수 있으며 dialogview를 사용하여 구현하였습니다. 반려 식물 리스트를 클릭하면 해당 식물의 정보를 볼 수 있는 화면으로 넘어갑니다.
반려 식물에 대한 수정, 삭제도 가능합니다.

#🗓 다이어리 화면
<p>
<img width="200" alt="스크린샷 2021-02-14 오후 3 52 15" src="https://user-images.githubusercontent.com/61643122/107870924-5acc4c00-6ee0-11eb-94f6-f11985f3f28b.png">
<img width="200" alt="스크린샷 2021-02-14 오후 3 55 15" src="https://user-images.githubusercontent.com/61643122/107870928-6b7cc200-6ee0-11eb-8b52-ae07aec9ce58.png">
<img width="200" alt="스크린샷 2021-02-14 오후 3 52 58" src="https://user-images.githubusercontent.com/61643122/107870931-7172a300-6ee0-11eb-907d-a8d5d265c816.png">
<img width="200" alt="스크린샷 2021-02-14 오후 3 55 03" src="https://user-images.githubusercontent.com/61643122/107870927-6750a480-6ee0-11eb-9b70-e5b9e3c8ef17.png">
</p>
다이어리에서는  recyclerview를 통해 내가 추가한 반려식물 리스트를 불러옵니다. 이 때 반려식물 당 다이어리를 작성할 수 있습니다. 아이템을 선택하면 intent를 사용하여 값을 넘기고 해당 식물에 대한 다이어리 화면으로 넘어갑니다.<br>
페이지를 넘어간 후에는 나의 반려식물에 대한 다이어리를 추가할 수 있으며 글 하나를 선택하면 수정, 삭제를 할 수 있는 화면이 나타납니다.

#⏰ 알람 화면
<p>
<img width="200" alt="스크린샷 2021-02-14 오후 3 57 25" src="https://user-images.githubusercontent.com/61643122/107870992-037aab80-6ee1-11eb-9f79-c5fb37ea03ef.png">
<img width="200" alt="스크린샷 2021-02-14 오후 3 55 38" src="https://user-images.githubusercontent.com/61643122/107870994-09708c80-6ee1-11eb-8904-7903e45b7677.png">
<img width="200" alt="스크린샷 2021-02-14 오후 3 55 51" src="https://user-images.githubusercontent.com/61643122/107870996-0b3a5000-6ee1-11eb-9652-58fa4e000614.png">
<img width="200" alt="스크린샷 2021-02-14 오후 3 56 21" src="https://user-images.githubusercontent.com/61643122/107870993-05dd0580-6ee1-11eb-94a0-d2b0831cd81d.png">
</p>
알람 페이지에서는  알람을 등록할 식물을 선택하고, 날짜 등록 버튼을 누르면 원하는 날짜와 시간을 고를 수 있습니다. 알람을 등록하면 알람이 디비에 저장되며 recyclerview를 사용하여 등록된 알람 리스트를 불러옵니다. 
또한 알람을 삭제하게 된다면 디비 뿐만 아니라 기기에서도 모두 삭제가 되도록 구현하였습니다. <br>
알람을 설정한 시간이 되면 노래와 함께 알람이 실행됩니다. 앱의 다른 화면이나 핸드폰 화면이 꺼져 있을 때도 해당 시간에 정상 동작되며 하단에 있는 화살표 버튼을 통해 알람을 종료할 수 있습니다.

#👍 추천 화면 ,⚙ 설정 화면
<p>
<img width="200" alt="스크린샷 2021-02-14 오후 3 57 51" src="https://user-images.githubusercontent.com/61643122/107871029-6a986000-6ee1-11eb-9ecb-25a0c6fad811.png">
<img width="200" alt="스크린샷 2021-02-14 오후 3 58 04" src="https://user-images.githubusercontent.com/61643122/107871031-6c622380-6ee1-11eb-86ad-ffb15d3df5d2.png">
<img width="200" alt="스크린샷 2021-02-14 오후 3 58 19" src="https://user-images.githubusercontent.com/61643122/107871043-784de580-6ee1-11eb-8a72-a66810423048.png">
</p>
<br>
추천 페이지에서는 6개의 카테고리로 나눠서 식물을 추천해줍니다. 원하는 카테고리를 선택하면 해당하는 식물들의 이미지, 종, 팁을 데이터베이스에서 불러와 recyclerview를 통해 나타나도록 구현하였습니다. <br>
설정 페이지에서는 로그아웃을 할 수 있으며 이용 약관과 제작과 소개를 확인할 수 있습니다. 또한, 로그아웃을 하게 된다면 로그인 화면으로 돌아가게 됩니다. 

#🙂 마무리
2020 겨울방학 Programming GURU 구루2 안드로이드 플렌테라피하시조 <br>
Developer - 김내림, 이민영 <br>
Design - 이유진

