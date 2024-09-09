async  function signUp() {

    const user_dto = {
        first_name: document.getElementById("firstName").value,
        last_name: document.getElementById("lastName").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value,
    };



    const  response = await  fetch(
            "SignUp",
            {
                method: "POST",
                body: JSON.stringify(user_dto),
                headers: {
                    "content-type": "application/json"
                }
            }
    );

    if (response.ok) {
        const json = await response.json();
        if (json.Success) {
            window.location = "verify-account.html";
        } else {
            document.getElementById("message").innerHTML = json.content;
        }

    } else {
        document.getElementById("message").innerHTML = "Please Try again Later!";
    }
}


