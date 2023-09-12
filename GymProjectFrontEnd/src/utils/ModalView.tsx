import * as React from 'react';
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Button from '@mui/material/Button';
import { TableContainer, Paper, Table, TableHead, TableRow, TableCell, TableBody, FormControlLabel, Checkbox, TextField } from '@mui/material';
import GymPlan from '../models/GymPlan';

const style = {
  position: 'absolute' as 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  pt: 2,
  px: 4,
  pb: 3,
};


export const NestedModal = () => {
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };

 

  const [newPlan, setNewPlan] = React.useState<GymPlan>();
  const loadedGymPlan= new GymPlan();  
  const authToken = localStorage.getItem("authenticationToken")  
  const userName = localStorage.getItem("username");


  async function addNewPlan(){
    const tryAddingNewPlan= async()=>{
      const result = await fetch("http://localhost:8080/api/gym/addnewplan",
    {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${authToken}`,
        'UserName': `${userName}`
      },
      body: JSON.stringify({

        "body_part": newPlan?.body_part,
        "exercise1": newPlan?.exercise1+'$0',
        "exercise2": newPlan?.exercise2+'$0',
        "exercise3": newPlan?.exercise3+'$0',
        "exercise4": newPlan?.exercise4+'$0',
        "exercise5": newPlan?.exercise5+'$0'
      })
    });
    
      
   
      if(!result.ok){
        const data= await result.json()
        alert(data.message)
        throw new Error("reached limit")
      }
      console.log("data pushed  "+result);
     

    // this line redirect to home page
    window.location.href = "http://localhost:3000/workout";
    }
    
    tryAddingNewPlan().catch((error: any) => {
      console.log(error)
    })

  }

  //to check how many fields has been filled in the form
  function countCheck(){
    if(newPlan?.body_part &&
      newPlan?.exercise1  &&
      newPlan?.exercise2  &&
      newPlan?.exercise3  &&
      newPlan?.exercise4  &&
      newPlan?.exercise5  
      )
    return false;
    
    return true;
  }



  return (
    <div>
      <Button onClick={handleOpen}>Add New Plan</Button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="parent-modal-title"
        aria-describedby="parent-modal-description"
      >
        {/* here i can adjust width and height of modal box */}
        <Box sx={{ ...style, width: 800 }}>
          <h2 id="parent-modal-title">Fill the below Form</h2>

          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>Body Target</TableCell>
                  <TableCell><TextField id="outlined-basic1" size="small"  value={newPlan?.body_part} onChange={(e)=>{  setNewPlan(prevPlan=>{ return {...prevPlan,body_part:e.target.value}}); console.log('body part  '+newPlan?.body_part)}} variant="outlined"  /></TableCell>
                        
                </TableRow>
                <TableRow>
                
                  <TableCell>Exercise1</TableCell>
                  <TableCell><TextField id="outlined-basic1" size="small" value={newPlan?.exercise1} onChange={(e)=>{setNewPlan(prevPlan=>{ return {...prevPlan,exercise1:e.target.value}});}} variant="outlined" /></TableCell>
                </TableRow>
                <TableRow>
                
                  <TableCell>Exercise2</TableCell>
                  <TableCell><TextField id="outlined-basic1" size="small" value={newPlan?.exercise2} onChange={(e)=>{setNewPlan(prevPlan=>{ return {...prevPlan,exercise2:e.target.value}});}} variant="outlined" /></TableCell>
                </TableRow>
                <TableRow>
                
                  <TableCell>Exercise3</TableCell>
                  <TableCell><TextField id="outlined-basic1" size="small" value={newPlan?.exercise3} onChange={(e)=>{setNewPlan(prevPlan=>{ return {...prevPlan,exercise3:e.target.value}});}} variant="outlined" /></TableCell>
                </TableRow>
                <TableRow>
                
                  <TableCell>Exercise4</TableCell>
                  <TableCell><TextField id="outlined-basic1" size="small" value={newPlan?.exercise4} onChange={(e)=>{setNewPlan(prevPlan=>{ return {...prevPlan,exercise4:e.target.value}});}} variant="outlined" /></TableCell>
                </TableRow>
                <TableRow>
                
                  <TableCell>Exercise5</TableCell>
                  <TableCell><TextField id="outlined-basic1" size="small" value={newPlan?.exercise5} onChange={(e)=>{setNewPlan(prevPlan=>{ return {...prevPlan,exercise5:e.target.value}});}} variant="outlined" /></TableCell>
                </TableRow>
              </TableHead>
            </Table>
          </TableContainer>

          <Button variant="contained" disabled={countCheck()} onClick={() => { addNewPlan(); }}>Submit</Button> 

        </Box>
      </Modal>
    </div>
  );
}

