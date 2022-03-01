


$(document).ready(function() {

    console.log($('#serialNumbers').val())
    var serialNumbers = $('#serialNumbers').val().replace('[','').replace(']','').replace(' ', '').split(',');
    console.log(serialNumbers);


    // setupUpdateButtonClickEvents = function() {
    //     // collect all inputs and buttons so we can make them unique.
    //     var modalButtons = $('[id="submit-schedule"]');
    //     var mondayInputs = $('[id="M"]');
    //     var tuesdayInputs = $('[id="T"]');
    //     var wednesdayInputs = $('[id="W"]');
    //     var thursdayInput = $('[id="Th"]');
    //     var fridayInputs = $('[id="F"]');
    //     var saturdayInputs = $('[id="S"]');
    //     var sundayInputs = $('[id="Sun"]');
    //     var timeInputs = $('[id="appt"]');
    //     var freqInputs = $('[id="freq"]');

    //     var numModalButtons = modalButtons.length;
    //     for (i = 0; i < numModalButtons; i++) {
    //         // queue up variables for submission
    //         var inputNum = serialNumbers[i];
            
    //         // set new Ids
    //         mondayInputs[i].id = 'M-' + inputNum;
    //         tuesdayInputs[i].id = 'T-' + inputNum;
    //         wednesdayInputs[i].id = 'W-' + inputNum;
    //         thursdayInput[i].id = 'Th-' + inputNum;
    //         fridayInputs[i].id = 'F-' + inputNum;
    //         saturdayInputs[i].id = 'S-' + inputNum;
    //         sundayInputs[i].id = 'Sun-' + inputNum;
    //         modalButtons[i].id = 'submit-schedule-'+ inputNum;
    //         timeInputs[i].id = 'appt-' + inputNum;
    //         freqInputs[i].id = 'freq-' + inputNum;

    //         // add event listeners
    //         modalButtons[i].addEventListener("click", function() {
    //             submitSchedule(inputNum);
    //         })
    //     }
    // }

    setupDeleteButtonClickEvents = function() {
        
        var buttons = $('[id="delete-schedule-button"]');
        var numButtons = buttons.length;

        for(i = 0; i < numButtons; i++) {
            buttons[i].id = 'delete-schedule-button-'+serialNumbers[i];
            var inputNum = serialNumbers[i];
            buttons[i].addEventListener("click", function() {
                console.log(this.id + ' clicked with serialNumber: ' + inputNum);
                deleteDevice(inputNum);
            });
        }   
    } 

    setupDeleteButtonClickEvents();
    // setupUpdateButtonClickEvents();

 

    $("#submit-schedule").click(function() {
        var m = $('#M')[0]?.value ?? null;
        var t = $('#T')[0]?.value ?? null;
        var w = $('#W')[0]?.value ?? null;
        var th = $('#Th')[0]?.value ?? null;
        var f= $('#F')[0]?.value ?? null;
        var s = $('#S')[0]?.value ?? null;
        var sun = $('#Sun')[0]?.value ?? null;

        var time = $('#appt').val();
        var freq = $('#freq option:selected').val();

        $.ajax({
            type: 'POST',
            dataType: 'json',
            data: { M: m, T:t, W:w, Th:th, F:f, S:s, Sun:sun, appt: time, freq: freq },
            url: '/Schedule',
            success: () => {
                console.log('successful post');
            },
            error: (err) => console.log(err)
        });
    });
    

    deleteDevice = function(serialNumber) {    
        toastr.info('Deleting Device ' + serialNumber);
        $.ajax({
            type: 'POST',
            dataType: 'json',
            data: { serialNumber: serialNumber },
            url: '/DeleteDevice',
            success: () => {
                toastr.info('Device ' + serialNumber + ' deleted successfully.')
                console.log('Successfully Deleted Device.');
                location.reload();
            },
            error: (err) => {
                // toastr.error('Failed to Delete Device ' + serialNumber);
                // console.log(err);
                location.reload();
            }
        })
    };   
});

