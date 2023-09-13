import * as React from 'react';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import GymPlan from './GymPlan';
import { useEffect } from 'react';




const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}));


/**
 * 
 * [
    {
        "gymPlanPrimaryKey": {
            "userId": 4,
            "exerciseId": 0
        },
        "body_part": "anshu1",
        "exercise1": "1$0",
        "exercise2": "1$0",
        "exercise3": "1$0",
        "exercise4": "11$0",
        "exercise5": "1$0"
    }
]
 * 
 */







export default function GymplanTable() {

  const authToken = localStorage.getItem("authenticationToken");
  const userName = localStorage.getItem("username");
  const [gymPlan, setGymplan] = React.useState<GymPlan[]>([]);

  useEffect(() => {
    // Perform asynchronous operation, e.g., fetch data
    const fetchAllGymPlan = async () => {

      const url: string = "http://localhost:8080/api/gym/user/gymPlans";

      const response = await fetch(url, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${authToken}`,
          'UserName': `${userName}`
        },
      });

      if (!response.ok) {
        throw new Error('Something went wrong to fetch all gym plans data!');
      }

      const responseJson = await response.json();

      const LoadedGymPlan: GymPlan[] = [];


      await responseJson.forEach((Plan: GymPlan) => {
        LoadedGymPlan.push({
          gymPlanPrimaryKey: Plan.gymPlanPrimaryKey,
          body_part: Plan.body_part,
          exercise1: Plan.exercise1?.split("$")[0],
          exercise2: Plan.exercise2?.split("$")[0],
          exercise3: Plan.exercise3?.split("$")[0],
          exercise4: Plan.exercise4?.split("$")[0],
          exercise5: Plan.exercise5?.split("$")[0]
        });

      });

      setGymplan(LoadedGymPlan);
    }

    fetchAllGymPlan().catch((error: any) => {
      console.log(error)
    })

  }, []);


  async function handleClick(exerciseId: number | undefined) {

    const baseUrl: string = "http://localhost:8080/api/gym/remove";
    const url: string = `${baseUrl}/${exerciseId}`;

      const response = await fetch(url, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${authToken}`,
          'UserName': `${userName}`
        },
      });

      if (!response.ok) {
        const data= await response.json()
        alert(data.message)
        throw new Error('Something went wrong to fetch all gym plans data!');
      }
      else if (response.ok) {
        alert("sucessfully deleted a plan!")
      }

  }

  return (

    <div>
      {Object.keys(gymPlan).length > 0 &&
        <TableBody>
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 700 }} aria-label="customized table">
              <TableHead>
                <TableRow>
                  <StyledTableCell>Body Target</StyledTableCell>
                  <StyledTableCell align="right">exercise1</StyledTableCell>
                  <StyledTableCell align="right">exercise2</StyledTableCell>
                  <StyledTableCell align="right">exercise3</StyledTableCell>
                  <StyledTableCell align="right">exercise4</StyledTableCell>
                  <StyledTableCell align="right">exercise5</StyledTableCell>
                  <StyledTableCell align="right">delete It?</StyledTableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {gymPlan.map((row) => (
                  <StyledTableRow key={row.gymPlanPrimaryKey?.exerciseId}>
                    <StyledTableCell component="th" scope="row">
                      {row.body_part}
                    </StyledTableCell>
                    <StyledTableCell align="right">{row.exercise1}</StyledTableCell>
                    <StyledTableCell align="right">{row.exercise2}</StyledTableCell>
                    <StyledTableCell align="right">{row.exercise3}</StyledTableCell>
                    <StyledTableCell align="right">{row.exercise4}</StyledTableCell>
                    <StyledTableCell align="right">{row.exercise5}</StyledTableCell>
                    <StyledTableCell align="right">
                      <td>
                        <button onClick={() => handleClick(row.gymPlanPrimaryKey?.exerciseId)}>Click here</button>
                      </td>
                    </StyledTableCell>
                  </StyledTableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </TableBody>
      }
    </div>

  );



}


