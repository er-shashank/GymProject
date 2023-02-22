import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Button, Checkbox, FormControlLabel, TextField } from '@mui/material';
import GymPlan from '../models/GymPlan';

function createData(
    name: string,
    Exercise1: string,
    Exercise2: string,
    Exercise3: string,
    Exercise4: string,
    Exercise5: string,
    Submission: string
) {
    return {
        name,
        Exercise1,
        Exercise2,
        Exercise3,
        Exercise4,
        Exercise5,
        Submission
    };
}

const rows = [
    createData('Frozen yoghurt', '159', '6.0', '24', '4.0', '1', '1')
];

export const WorkOutTable = (prop: any) => {

    const [selectedRadioBtn, setSelectedRadioBtn] = React.useState([false, false, false, false, false]);
    // const [personalRecord, setPersonalRecord] = React.useState(
    //     [prop.currentDay.exercise1.split("$")[1],
    //     prop.currentDay.exercise2.split("$")[1],
    //     prop.currentDay.exercise3.split("$")[1],
    //     prop.currentDay.exercise4.split("$")[1],
    //     prop.currentDay.exercise5.split("$")[1]]);

    // function updatePersonalRecord(personalRecord:any) {
    //     setPersonalRecord(personalRecord);
    // };

    function updateFormDataOnSubmit() {
        prop.changeDay(selectedRadioBtn, prop.currentDay);
        setSelectedRadioBtn([false, false, false, false, false]);

    }


    function countChecked() {
        let count = 0;
        for (let i = 0; i < selectedRadioBtn.length; i++) {
            if (selectedRadioBtn[i]) count++;
        }
        return count < 3;
    }

    return (

        <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Body Target</TableCell>
                        <TableCell align="right">Exercise1</TableCell>
                        <TableCell align="right">Exercise2</TableCell>
                        <TableCell align="right">Exercise3</TableCell>
                        <TableCell align="right">Exercise4</TableCell>
                        <TableCell align="right">Exercise5</TableCell>
                        <TableCell align="right">Submit</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>

                    <TableRow
                        key={1}
                        sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                    >
                        <TableCell component="th" scope="row">
                            {prop.currentDay.body_part}
                        </TableCell>
                        <TableCell align="right"><FormControlLabel control={<Checkbox checked={selectedRadioBtn[0]} onClick={() => { setSelectedRadioBtn([!selectedRadioBtn[0], selectedRadioBtn[1], selectedRadioBtn[2], selectedRadioBtn[3], selectedRadioBtn[4]]); }} />} label={prop.currentDay.exercise1.split("$")[0]} /></TableCell>
                        <TableCell align="right"><FormControlLabel control={<Checkbox checked={selectedRadioBtn[1]} onClick={() => { setSelectedRadioBtn([selectedRadioBtn[0], !selectedRadioBtn[1], selectedRadioBtn[2], selectedRadioBtn[3], selectedRadioBtn[4]]); }} />} label={prop.currentDay.exercise2.split("$")[0]} /></TableCell>
                        <TableCell align="right"><FormControlLabel control={<Checkbox checked={selectedRadioBtn[2]} onClick={() => { setSelectedRadioBtn([selectedRadioBtn[0], selectedRadioBtn[1], !selectedRadioBtn[2], selectedRadioBtn[3], selectedRadioBtn[4]]); }} />} label={prop.currentDay.exercise3.split("$")[0]} /></TableCell>
                        <TableCell align="right"><FormControlLabel control={<Checkbox checked={selectedRadioBtn[3]} onClick={() => { setSelectedRadioBtn([selectedRadioBtn[0], selectedRadioBtn[1], selectedRadioBtn[2], !selectedRadioBtn[3], selectedRadioBtn[4]]); }} />} label={prop.currentDay.exercise4.split("$")[0]} /></TableCell>
                        <TableCell align="right"><FormControlLabel control={<Checkbox checked={selectedRadioBtn[4]} onClick={() => { setSelectedRadioBtn([selectedRadioBtn[0], selectedRadioBtn[1], selectedRadioBtn[2], selectedRadioBtn[3], !selectedRadioBtn[4]]); }} />} label={prop.currentDay.exercise5.split("$")[0]} /> </TableCell>
                        <TableCell align="right"><Button variant="contained" disabled={countChecked()} onClick={() => { updateFormDataOnSubmit(); }}>Submit</Button> </TableCell>

                    </TableRow>
                    <TableRow
                        key={2}
                        sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                    >
                        <TableCell component="th" scope="row">Current PR</TableCell>
                        <TableCell align="right"><TextField id="outlined-basic1" size="small" disabled={!selectedRadioBtn[0]} value={prop.personalRecord[0]} onChange={(e)=>{prop.setPersonalRecord([e.target.value,prop.personalRecord[1],prop.personalRecord[2],prop.personalRecord[3],prop.personalRecord[4]])}} variant="outlined" /> </TableCell>
                        <TableCell align="right"><TextField id="outlined-basic2" size="small" disabled={!selectedRadioBtn[1]} value={prop.personalRecord[1]} onChange={(e)=>{prop.setPersonalRecord([prop.personalRecord[0],e.target.value,prop.personalRecord[2],prop.personalRecord[3],prop.personalRecord[4]])}} variant="outlined" /> </TableCell>
                        <TableCell align="right"><TextField id="outlined-basic3" size="small" disabled={!selectedRadioBtn[2]} value={prop.personalRecord[2]} onChange={(e)=>{prop.setPersonalRecord([prop.personalRecord[0],prop.personalRecord[1],e.target.value,prop.personalRecord[3],prop.personalRecord[4]])}} variant="outlined" /></TableCell>
                        <TableCell align="right"><TextField id="outlined-basic4" size="small" disabled={!selectedRadioBtn[3]} value={prop.personalRecord[3]} onChange={(e)=>{prop.setPersonalRecord([prop.personalRecord[0],prop.personalRecord[1],prop.personalRecord[2],e.target.value,prop.personalRecord[4]])}} variant="outlined" /></TableCell>
                        <TableCell align="right"><TextField id="outlined-basic5" size="small" disabled={!selectedRadioBtn[4]} value={prop.personalRecord[4]} onChange={(e)=>{prop.setPersonalRecord([prop.personalRecord[0],prop.personalRecord[1],prop.personalRecord[2],prop.personalRecord[3],e.target.value])}} variant="outlined" /></TableCell>

                    </TableRow>

                </TableBody>
            </Table>
        </TableContainer>
    );
}