function checkValues(){
    var isPrevented = false;
    checkIsEmptyField(document.getElementById('loginInput')) ;
    checkIsEmptyField(document.getElementById('passwordInput'));
    checkIsEmptyField(document.getElementById('emailInput'));
    checkIsEmptyField(document.getElementById('nameInput'));
    checkIsEmptyField(document.getElementById('surnameInput'));
    checkIsEmptyField(document.getElementById('patronymicInput'));
    checkIsEmptyField(document.getElementById('phoneInput'));

    if (isPrevented){
        event.preventDefault();
        var errorElement = document.getElementById('error-empty_field');
        errorElement.textContent = 'Fields shouldn\'t be empty';
    }

    function checkIsEmptyField(element) {
        if (!element.value){
            markField(element);
            isPrevented = true;
        }
        else {
            unmarkField(element);
        }
    }

    function unmarkField(element) {
        element.style.borderColor = '#AAA';
    }

    function markField(element){
        element.style.borderColor = 'red';
    }

}

