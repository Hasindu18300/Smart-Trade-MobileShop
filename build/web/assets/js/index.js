async  function checkSignIn() {

    const response = await fetch("CheckSignIn");
    if (response.ok) {

        const json = await response.json();
        console.log(user);
        
        const response_dto = json.response_dto;
        const productList = json.product;

        if (response_dto.success) {
            //sign in

            const user = response_dto.content;


            let st_quick_link = document.getElementById("st-quick-link");

            let st_quick_link_li_1 = document.getElementById("st-quick-link-li-1");
            let st_quick_link_li_2 = document.getElementById("st-quick-link-li-2");

            st_quick_link_li_1.remove();
            st_quick_link_li_2.remove();

            let new_li_tag1 = document.getElementById("li");
            new_li_tag1.innerHTML = user.first_name + " " + user.last_name;
            st_quick_link.appendChild(new_li_tag1);

            let st_button_1 = document.getElementById("st-button-1");
            st_button_1.href = "sign Out";
            st_button_1.innerHTML = "sign Out";

            st_div_1 = document.getElementById("st-div-1");
            st_div_1.remove();


        } else {

            console.log("heei");
        }
        
        

    }
}

//function checkSignIn() {
//    fetch("CheckSignIn").then(
//            response => {
//                if (response.ok) {
//                    return response.json();
//                } else {
//                    throw new Error();
//                }
//            }
//    ).then(
//            json => {
//                if (json.success) {
//                    //Sign In
//                    const user = json.content;
//                    console.log(user);
//
//                    let st_quick_link = document.getElementById("st-quick-link");
//                    let st_quick_link_li_1 = document.getElementById("st-quick-link-li-1");
//                    let st_quick_link_li_2 = document.getElementById("st-quick-link-li-2");
//
//                    st_quick_link_li_1.remove();
//                    st_quick_link_li_2.remove();
//
//                    let new_li_tag1 = document.createElement("li");
//                    let new_li_a_tag = document.createElement("a");
//                    new_li_a_tag.href = "#";
//                    new_li_tag1.innerHTML = user.first_name + " " + user.last_name;
//                    new_li_tag1.appendChild(new_li_a_tag);
//                    st_quick_link.appendChild(new_li_tag1);
//
//                    let st_button_1 = document.getElementById("st-button-1");
//                    st_button_1.href = "Sign Out";
//                    st_button_1.innerHTML = "Sign Out";
//
//                    let st_div_1 = document.getElementById("st-div-1");
//                    st_div_1.remove();
//
//                } else {
//                    //Not sign in
//
//                    console.log("Not signIn");
//                }
//            }
//    ).catch(
//            error => {
//                console.log(error);
//            }
//    );
//
//
//}