import React from "react";

export const RadioButton: React.FC<{changeExerciseCount: Function, exercise: string }>  = (props:{ changeExerciseCount: Function, exercise: string }) => {

    const [selectedRadioBtn, setSelectedRadioBtn] = React.useState(["","","","",""]);

    const isRadioSelected = (value: string): boolean => selectedRadioBtn.includes(value) ;
    const buttonNo:number= parseInt(props.exercise)
    
    if(props.exercise=="none"){alert("worked")}
    const resetCheckBox= ()=>{
        setSelectedRadioBtn(["","","","",""])
    }
    
    return (
        <div>
            <input type="checkbox"
                name="react-radio-btn"
                value={props.exercise}
                defaultChecked={false}
                 checked= {isRadioSelected(props.exercise)}
                onClick={()=>{
                    if(selectedRadioBtn[buttonNo-1]==""){
                        selectedRadioBtn[buttonNo-1]=props.exercise
                        setSelectedRadioBtn(selectedRadioBtn)
                    }
                    
                    else
                    {   selectedRadioBtn[buttonNo-1]=""
                        setSelectedRadioBtn(selectedRadioBtn)
                    }
                    props.changeExerciseCount(isRadioSelected(props.exercise)
                    
                    )
                }}

            />
        </div>
    )
}