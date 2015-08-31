function formValidator(){
           
        	var u_name = document.getElementById('u_name');
        	
        	if(!notEmpty(u_name,'Please Select a User!')){
				
        		document.getElementById('h1img').style.display = 'none';
				document.getElementById('h1').style.display = 'inline';
				
			}
			else if(!radiocheck()){
				document.getElementById('h2img').style.display = 'none';
				document.getElementById('h2').style.display = 'inline';
			}
			else
				document.getElementById('doReview').submit();
        }

        function notEmpty(elem, helperMsg){
        	if(elem.value.length == 0){
        		return false;
        	}
        	return true;
        }

        function radiocheck(){
        var choice = document.getElementsByName("choice");
		var flag = false;
        for(var i = 0; i < choice.length; i++) {
           if(choice[i].checked == true) {
               selectedChoice = choice[i].value;
               flag = true;
           }
        }
        return flag;
         }

        function hideError(){
        	document.getElementById('h1').style.display = 'none';
        	document.getElementById('h1img').style.display = 'inline';
        }

        function hidemsg(){
        	document.getElementById('h2img').style.display = 'inline';
        	document.getElementById('h2').style.display = 'none';
        }