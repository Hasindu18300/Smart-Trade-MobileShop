async function loadData() {
    const popup = Notification();

    const response = await fetch(
            "LoadCheckOut"
            );

    if (response.ok) {

        const json = response.json();
        //console.log(json);

        if (json.success) {


            //store response data
            address = json.address;
            const  cityList = json.cityList;
            const  cartList = json.cartList;

        } else {
            window.location = "sign-in.htlm";

        }
    }
}