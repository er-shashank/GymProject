//this contains complete page ---> workout table+ show history

import { Button, Toolbar } from "@mui/material"
import { Route, NavLink } from "react-router-dom"
import { WorkOutTable } from "./WorkOutTable"
import { useState, useEffect } from "react"
import GymPlan from "../models/GymPlan"
import { SpinnerLoading } from "./SpinnerLoading"
import { resetGymplanFunction } from "./UtilityFunctions/ResetGymplanFunction"
import { NestedModal } from "./ModalView"
import { Home } from "@mui/icons-material"
import { LogoutFunction } from "./UtilityFunctions/LogoutFunction"

export const WorkOutPage = () => {

    const [currentDay, setCurrentday] = useState<GymPlan>()
    const [dayNo, setDayNo] = useState(1);
    const [isLoading, setLoading] = useState(true);
    const [httpError, setHttpError] = useState(null);
    const [showHistory, setShowHistory] = useState(false);
    const [personalRecord, setPersonalRecord] = useState(['', '', '', '', '']);
    const authToken = localStorage.getItem("authenticationToken")
    const userName = localStorage.getItem("username");

    const changeDay = (selectedRadioBtn: any, currentDay: GymPlan) => {

        fetch("http://localhost:8080/api/gym/updatepr",
            {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${authToken}`,
                    'UserName': `${userName}`
                },
                body: JSON.stringify({

                    "id": currentDay.id,
                    "body_part": currentDay.body_part,
                    "exercise1": personalRecord[0] == "" ? currentDay.exercise1 : currentDay.exercise1?.split("$")[0] + "$" + personalRecord[0],
                    "exercise2": personalRecord[1] == "" ? currentDay.exercise2 : currentDay.exercise2?.split("$")[0] + "$" + personalRecord[1],
                    "exercise3": personalRecord[2] == "" ? currentDay.exercise3 : currentDay.exercise3?.split("$")[0] + "$" + personalRecord[2],
                    "exercise4": personalRecord[3] == "" ? currentDay.exercise4 : currentDay.exercise4?.split("$")[0] + "$" + personalRecord[3],
                    "exercise5": personalRecord[4] == "" ? currentDay.exercise5 : currentDay.exercise5?.split("$")[0] + "$" + personalRecord[4]
                })
            }).then((result) => {
                console.log("data pushed  " + result);
            }).catch((err) => {
                console.error(err);
            });


        setDayNo(((dayNo + 1) % 3) + 1)


        //pushing data to record
        let currenDate = new Date();
        let date = currenDate.getFullYear() + '-' + (currenDate.getMonth() + 1) + '-' + currenDate.getDate();
        console.log(personalRecord);
        fetch("http://localhost:8080/api/gym/gymhistory",
            {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${authToken}`,
                    'UserName': `${userName}`
                },
                body: JSON.stringify({

                    "exercise_id": currentDay.id,
                    "body_part": currentDay.body_part,
                    "date": date,
                    "exercise1": selectedRadioBtn[0] ? currentDay.exercise1 : "NA",
                    "exercise2": selectedRadioBtn[1] ? currentDay.exercise2 : "NA",
                    "exercise3": selectedRadioBtn[2] ? currentDay.exercise3 : "NA",
                    "exercise4": selectedRadioBtn[3] ? currentDay.exercise4 : "NA",
                    "exercise5": selectedRadioBtn[4] ? currentDay.exercise5 : "NA"
                })
            }).then((result) => {
                console.log("data pushed  ")
            }).catch((err) => {
                console.error(err);
            });

    }



    useEffect(() => {
        const fetchBooks = async () => {

            const nextWorkoutUrl: string = "http://localhost:8080/api/gym/nextwork";
            const response = await fetch(nextWorkoutUrl, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'UserName': `${userName}`
                },
            });

            if (response.ok) {
                const data = await response.json();
                setDayNo(data);
            } else {
                console.error('Request failed:', response.status);
            }


            const baseUrl: string = "http://localhost:8080/api/gym/gymplan";
            const url: string = `${baseUrl}?id=${dayNo}`;
            const reponse = await fetch(url, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'UserName': `${userName}`
                },
            });

            if (!reponse.ok) {
                throw new Error('something went wrong today')
            }

            const responseJson = await reponse.json();
            const responseData = responseJson;
            const loadedGymPlan: GymPlan = responseData;


            setCurrentday(loadedGymPlan);
            setPersonalRecord([loadedGymPlan.exercise1?.split("$")[1]!,
            loadedGymPlan.exercise2?.split("$")[1]!,
            loadedGymPlan.exercise3?.split("$")[1]!,
            loadedGymPlan.exercise4?.split("$")[1]!,
            loadedGymPlan.exercise5?.split("$")[1]!]);
            setLoading(false);
        };

        fetchBooks().catch((error: any) => {
            setLoading(false);
            setHttpError(error.message);
        })
    }, [dayNo])




    if (isLoading) {
        return (
            <SpinnerLoading></SpinnerLoading>
        )
    }



    if (httpError) {
        return (
            <div className='container m-5'>
                <p>{httpError}</p>
            </div>
        )
    }




    return (

        <>

            <NavLink to='/'>  <Button
                variant="contained"
                color="primary"
                startIcon={<Home />}
                className="home-button"
            ></Button> </NavLink>
            
            <WorkOutTable currentDay={currentDay} changeDay={changeDay} personalRecord={personalRecord} setPersonalRecord={setPersonalRecord}></WorkOutTable>

            <NavLink to='/history'> <Button variant="contained" >{showHistory ? 'Close History' : 'Show History'} </Button></NavLink>

            <NavLink to='/login'> <Button variant="contained" onClick={LogoutFunction}>Logout </Button></NavLink>


            <Toolbar sx={{ justifyContent: "space-between" }}>
                <div />
                <Button variant="contained" onClick={resetGymplanFunction}>Reset</Button>
            </Toolbar>

            <NestedModal></NestedModal>

        </>
    );
}