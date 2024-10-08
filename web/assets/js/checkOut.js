const popup = Notification();

async function loadData() {

    const response = await fetch(
            "LoadCheckOut"
            );

    if (response.ok) {

        const json = await  response.json();
        console.log(json);

        if (json.success) {

            //store response data
            address = json.address;
            const  cityList = json.cityList;
            const  cartList = json.cartList;

            //load cities
            let citySelect = document.getElementById("city");
            citySelect.length = 1;

            cityList.forEach(city => {
                let cityOption = document.createElement("option");
                cityOption.value = city.id;
                cityOption.innerHTML = city.name;
                citySelect.appendChild(cityOption);
            });

            //load current address

            let currentAddressCheckBox = document.getElementById("checkbox1");
            currentAddressCheckBox.addEventListener("change", e => {

                let first_name = document.getElementById("first-name");
                let last_name = document.getElementById("last-name");
                let city = document.getElementById("city");
                let address1 = document.getElementById("address1");
                let address2 = document.getElementById("address2");
                let postal_code = document.getElementById("postal-code");
                let mobile = document.getElementById("mobile");

                if (currentAddressCheckBox.checked) {
                    first_name.value = address.first_name;
                    last_name.value = address.last_name;

                    city.value = address.city.id;
                    city.disabled = true;
                    city.dispatchEvent(new Event("change"));

                    address1.value = address.line1;
                    address2.value = address.line2;
                    postal_code.value = address.postal_code;
                    mobile.value = address.mobile;

                } else {
                    first_name.value = "";
                    last_name.value = "";
                    city.value = 0;
                    city.disabled = false;
                    city.dispatchEvent(new Event("change"));
                    address1.value = "";
                    address2.value = "";
                    postal_code.value = "";
                    mobile.value = "";
                }
            });

            //cart Item Load

            let st_tbody = document.getElementById("st-tbody");
            let st_item_tr = document.getElementById("st-item-tr");
            let st_order_subtotle_tr = document.getElementById("st-order-subtotle-tr");
            let st_order_shipping_tr = document.getElementById("st-order-shipping-tr");
            let st_order_totle_tr = document.getElementById("st-order-totle-tr");
            st_tbody.innerHTML = "";

            let sub_total = 0;

            cartList.forEach(item => {

                let st_item_clone = st_item_tr.cloneNode(true);
                st_item_clone.querySelector("#st-item-title").innerHTML = item.product.title;
                st_item_clone.querySelector("#st-item-qty").innerHTML = item.qty;

                let item_sub_total = item.product.price * item.qty;
                sub_total += item_sub_total;

                st_item_clone.querySelector("#st-item-subtotal").innerHTML = item_sub_total;

                st_tbody.appendChild(st_item_clone);

            });

            st_order_subtotle_tr.querySelector("#st-subtotal").innerHTML = sub_total;
            st_tbody.appendChild(st_order_subtotle_tr);

            //shipping amount change city change

            citySelect.addEventListener("change", e => {

                //update shipping charges
                //get cart item count

                let item_count = cartList.length;

                let shipping_amount = 0;

                if (citySelect.value == 3) {
                    //colombo
                    shipping_amount = item_count * 1000;


                } else {
                    //not colombo
                    shipping_amount = item_count * 2500;

                }

                st_order_shipping_tr.querySelector("#st-shipping-amount").innerHTML = shipping_amount;
                st_tbody.appendChild(st_order_shipping_tr);

                let total = shipping_amount + sub_total;

                st_order_totle_tr.querySelector("#st-total").innerHTML = total;
                st_tbody.appendChild(st_order_totle_tr);

            });

            city.dispatchEvent(new Event("change"));


        } else {
            window.location = "sign-in.html";
            //console.log("error")

        }
    }
}

async  function checkout() {


    //check address
    let isCurrentAddress = document.getElementById("checkbox1").checked;

    //get address data
    let first_name = document.getElementById("first-name");
    let last_name = document.getElementById("last-name");
    let city = document.getElementById("city");
    let address1 = document.getElementById("address1");
    let address2 = document.getElementById("address2");
    let postal_code = document.getElementById("postal-code");
    let mobile = document.getElementById("mobile");

    //request data
    const data = {

        isCurrentAddress: isCurrentAddress,
        first_name: first_name.value,
        last_name: last_name.value,
        city_id: city.value,
        address1: address1.value,
        address2: address2.value,
        postal_code: postal_code.value,
        mobile: mobile.value,
    };

    const  response = await  fetch(
            "Checkout",
            {
                method: "POST",
                body: JSON.stringify(data),
                headers: {
                    "content-type": "application/json"
                }
            }
    );


    if (response.ok) {

        const json = await  response.json();
        console.log(json);

    } else {
        console.log("try again later");
    }


}