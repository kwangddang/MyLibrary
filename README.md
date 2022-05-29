# 실행영상</br>
[![동작](http://img.youtube.com/vi/tnJn8fnq480/0.jpg)](https://www.youtube.com/watch?v=tnJn8fnq480) 
</br></br></br>
# 화면구성</br>
<img src="https://user-images.githubusercontent.com/77563227/157426414-e71c94d1-857f-43e8-83b6-a4af710b7acd.jpg" width="270" height="480"> <img src="https://user-images.githubusercontent.com/77563227/157428371-12147dd5-2cc6-476d-8fd1-bc19f5a89b07.jpg" width="270" height="480"> <img src="https://user-images.githubusercontent.com/77563227/157429327-52c4d4a5-45e2-4a2e-9f46-16069c16fbbf.jpg" width="270" height="480"></br>
1. 초기화면</br></br>
	이메일과 페이스북 아이디를 통해 회원가입 및 로그인을 할 수 있습니다.</br></br>
	계정을 생성하지 않고 비계정으로도 진행할 수 있습니다.</br></br></br>

2. 홈화면 </br></br>
	파이어베이스에 저장되어 있는 도서를 불러옵니다.</br></br>
	북마크가 많은 순서대로 9권을 불러옵니다.</br></br></br>

3. 검색화면 </br></br>
	 네이버 책 검색 API를 통해 검색 정보에 따른 책을 불러옵니다.</br></br>
<img src="https://user-images.githubusercontent.com/77563227/157430220-f2f79251-2c60-4efe-965d-36345d360660.jpg" width="270" height="480"> <img src="https://user-images.githubusercontent.com/77563227/157430226-9b174948-37a4-49da-86e6-9aa7d0b12937.jpg" width="270" height="480"> <img src="https://user-images.githubusercontent.com/77563227/157430236-e353dd21-4d65-4fcc-960d-961133a57f22.jpg" width="270" height="480"></br>
4. 책 상세화면 
 </br></br> 
	상세화면은 네이버에서 불러온 책 정보와 파이어베이스에 저장되어 있는 후기, 별점, 북마크 상태를 불러옵니다. </br></br>
	(비계정일시 북마크는 Room에 저장되어 있는 정보를 가져와 표시합니다.)</br></br>
<img src="https://user-images.githubusercontent.com/77563227/157431842-73a55aa1-a224-4827-9fab-b2bb8d0ff078.jpg" width="270" height="480"> <img src="https://user-images.githubusercontent.com/77563227/157431845-95187874-72f6-44b3-b97b-62282a8d7cad.jpg" width="270" height="480"></br>
5. 유저화면 
   </br></br> 
   파이어베이스에 저장되어 있는 유저의 이름과 책, 카테고리를 불러옵니다.</br></br>
   (비계정인 경우 Room 에 저장되어 있는 유저의 이름과 책, 카테고리를 불러옵니다.)</br></br>
 
6. 설정화면 </br></br> 
	닉네임을 변경할 수 있고, 로그아웃을 할 수 있습니다. </br></br>
	</br></br>
	# 사용한 기술
	- Firebase
	- MVVM Design Pattern
	- DataBinding
	- LiveData
	- Room
	- Hilt
	- Retrofit
	- NavigationArgs
	- Glide
	- Paging
	- LottieAnimationView
	- RxJava
	- Kotpref
