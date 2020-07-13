let index = {
	init:function(){
		$("#btn-save").on("click",()=>{ // this를 바인딩하기 위해 ()=>{}사용
			this.save();
		});
		$("#btn-update").on("click",()=>{
			this.update();
		});
	},
	save:function(){
		let data = {
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		};
        console.log(data)
        // ajax통신을 이용, 3개의 데이터를 json으로 변경 후 insert 요청
        // ajax호출시 defalut가 비동기 호출
        $.ajax({
            //회원가입 수행 요청
            type:"POST",
            url:"/auth/joinProc",
            //http body 데이터, js 오브젝트를 json 문자열로 변환
            data:JSON.stringify(data),
            //body데이터가 어떤 타입인지(MIME)
            contentType:"application/json; charset=utf-8",
            //서버로 요청해서 응답 왔을때 모두 문자열로 온다. json 모양의 문자열로 온다면 js 오브젝트로 변환
            dataType:"json"
        }).done(function(resp){
            alert('Sign Up Success.');
            console.log(resp);
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
    update:function(){
		let data = {
            id: $("#id").val(),
            username: $("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		};
        console.log(data)
        $.ajax({
            type:"PUT",
            url:"/user",
            data:JSON.stringify(data),
            contentType:"application/json; charset=utf-8",
            dataType:"json"
        }).done(function(resp){
            alert('Update Success.');
            console.log(resp);
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
    
    
    
    
    
    //,
	// login:function(){
	// 	let data = {
	// 		username:$("#username").val(),
	// 		password:$("#password").val(),
	// 	};
    //     console.log(data)
    //     $.ajax({
    //         //로그인 수행 요청
    //         type:"POST",
    //         url:"/api/user/login",
    //         data:JSON.stringify(data),
    //         contentType:"application/json; charset=utf-8",
    //         dataType:"json"
    //     }).done(function(resp){
    //         alert('Sign In Success.');
    //         console.log(resp);
    //         location.href = "/";
    //     }).fail(function(error){
    //         alert(JSON.stringify(error));
    //     });
	// }
}

index.init();