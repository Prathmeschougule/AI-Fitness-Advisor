import {
  Box,
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
  Typography,
} from '@mui/material';
import React, { useState } from 'react';
import { addActivity } from '../services/api';

const ActivityForm = ({ onActivitiesAdded }) => {
  const [activity, setActivity] = useState({
    type: 'RUNNING',
    duration: '',
    caloriesBurned: '',
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(activity);
    try {
      // Ensure correct data types (int instead of string)
      const payload = {
        ...activity,
        duration: parseInt(activity.duration),
        caloriesBurned: parseInt(activity.caloriesBurned),

        
        
      };

      await addActivity(payload);
      onActivitiesAdded();
      setActivity({ type: 'RUNNING', duration: '', caloriesBurned: '' });
    } catch (error) {
      console.error('Error submitting activity:', error);
    }
  };

  return (
    <div>
      <Typography variant="h5" align="center" sx={{ mb: 2 }}>
        Add Activity
      </Typography>

      <Box component="form" onSubmit={handleSubmit} sx={{ mb: 4 }}>
        <FormControl fullWidth sx={{ mb: 2 }}>
          <InputLabel>Activity Type</InputLabel>
          <Select
            value={activity.type}
            label="Activity Type"
            onChange={(e) =>
              setActivity({ ...activity, type: e.target.value })
            }
          >
            <MenuItem value="RUNNING">Running</MenuItem>
            <MenuItem value="CYCLING">Cycling</MenuItem>
            <MenuItem value="WALKING">Walking</MenuItem>
          </Select>
        </FormControl>

        <TextField
          label="Duration (Minutes)"
          type="number"
          fullWidth
          sx={{ mb: 2 }}
          value={activity.duration}
          onChange={(e) =>
            setActivity({ ...activity, duration: e.target.value })
          }
        />

        <TextField
          label="Calories Burned"
          type="number"
          fullWidth
          sx={{ mb: 2 }}
          value={activity.caloriesBurned}
          onChange={(e) =>
            setActivity({ ...activity, caloriesBurned: e.target.value })
          }
        />

        <Button type="submit" variant="contained" color="secondary" fullWidth>
          Add Activity
        </Button>
      </Box>
    </div>
  );
};

export default ActivityForm;
