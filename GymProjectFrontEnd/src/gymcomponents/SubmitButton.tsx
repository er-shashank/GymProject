import React from "react";
import { RadioButton } from './RadioButton';

export const SubmitButton: React.FC<{changeDay: Function, exerciseCount:number}>  = (props) => {

    const [selectedRadioBtn, setSelectedRadioBtn] = React.useState(false);

    const isRadioSelected = (value: string): boolean => selectedRadioBtn == true;
    
    return (
        <div>

            

            <input type='button' 
                    className='btn btn-primary mt-3' 
                value='Submit'
                disabled={props.exerciseCount<=2}
                checked= {isRadioSelected("Submit")}
                onClick={()=>{
                    
                    props.changeDay()
                }}

            />
        </div>
    )
}