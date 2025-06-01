import { Box, Button, FormControl, InputLabel, MenuItem, TextField } from '@mui/material'
import React, { useState } from 'react'

const ActivityForm = ({onActivitiesAdded}) => {

    const [activity,setActivity]=useState({
        type: "RUNNING", duration : '' , caloriesBurned:'' ,
        additionalMetrics:{}
    })

    const handleSubmit = async(e)=>{
        e.preventDefault();
        try{
            //    await addActivity(activity) 
               onActivitiesAdded();
               setActivity({type: "RUNNING", duration : '' , caloriesBurned:'' });               
        }catch(error)
        {

        }
    }
  return (
    <div>
       <Box component= "form"  onSubmit={handleSubmit} sx={{mb:4}}>
                <FormControl fullWidth sx={{mb:2}}>
                    <InputLabel> Activity Type </InputLabel>
                    <select 
                    value={activity.type}               
                    onchange={(e)=> setActivity({...activity,type: e.target.value})}>
                       <MenuItem value="RUNNING"> Running </MenuItem>  
                       <MenuItem value="CYCLING "> Cycling </MenuItem> 
                       <MenuItem value="WALKING"> Walking </MenuItem> 
                    </select>
                </FormControl>
                <TextField
                   label="Duration (Minutes)"
                   type='number'
                   fullWidth
                   sx={{mb:2}}
                   value={activity.duration}
                    onchange={(e)=> setActivity({...activity,duration:e.target.value})}
                />

                <TextField
                   label="Calories Burned"
                   type='number'
                   fullWidth
                   sx={{mb:2}}
                   value={activity.caloriesBurned}
                    onchange={(e)=> setActivity({...activity,caloriesBurned:e.target.value})}
                />

                <Button variant="contained"
            color="secondary">Add</Button>
       </Box>
    </div>
  )
}

export default ActivityForm
