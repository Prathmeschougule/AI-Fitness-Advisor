import React, { useEffect, useState } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import { Box, Card, CardContent, Divider, Typography } from '@mui/material';
import { getActivityDetail } from '../services/api'; // adjust your import path

const ActivityDetails = () => {
  const { id } = useParams();
  const location = useLocation();
  // Try to get passed activity data from navigation state
  const passedActivity = location.state?.activity;

  const [activity, setActivity] = useState(passedActivity || null);
  const [loading, setLoading] = useState(!passedActivity);

  useEffect(() => {
    if (!passedActivity && id) {
      const fetchActivityRecommendation = async () => {
        try {
          setLoading(true);
          const response = await getActivityDetail(id);
          setActivity(response.data);
        } catch (error) {
          console.error('Error fetching activity:', error);
        } finally {
          setLoading(false);
        }
      };
      fetchActivityRecommendation();
    }
  }, [id, passedActivity]);

  if (!id) {
    return <Typography variant="h6" align="center" sx={{ mt: 4 }}>Invalid activity ID.</Typography>;
  }

  if (loading) {
    return <Typography variant="h6" align="center" sx={{ mt: 4 }}>Loading...</Typography>;
  }

  if (!activity) {
    return <Typography variant="h6" align="center" sx={{ mt: 4 }}>Activity not found.</Typography>;
  }

  return (
    <Box sx={{ maxWidth: 800, mx: 'auto', p: 2 }}>
      <Card sx={{ mb: 2 }}>
        <Typography variant="h5" gutterBottom>Activity Details</Typography>
        <Typography>Type: {activity.activityType}</Typography>
        <Typography>Date: {new Date(activity.createdAt).toLocaleString()}</Typography>
      </Card>

      <Card>
        <CardContent>
          <Typography variant="h5" gutterBottom>AI Recommendation</Typography>
          <Typography paragraph>{activity.recommendation}</Typography>

          <Divider sx={{ my: 2 }} />

          <Typography variant="h6" gutterBottom>Improvements</Typography>
          {activity.improvements?.length > 0 ? (
            activity.improvements.map((item, index) => (
              <Typography key={index} paragraph>📌 {item}</Typography>
            ))
          ) : (
            <Typography>No improvements available.</Typography>
          )}

          <Divider sx={{ my: 2 }} />

          <Typography variant="h6" gutterBottom>Suggestions</Typography>
          {activity.suggestions?.length > 0 ? (
            activity.suggestions.map((item, index) => (
              <Typography key={index} paragraph>📌 {item}</Typography>
            ))
          ) : (
            <Typography>No suggestions available.</Typography>
          )}

          <Divider sx={{ my: 2 }} />

          <Typography variant="h6" gutterBottom>Safety Guidelines</Typography>
          {activity.safety?.length > 0 ? (
            activity.safety.map((item, index) => (
              <Typography key={index} paragraph>📌 {item}</Typography>
            ))
          ) : (
            <Typography>No safety guidelines available.</Typography>
          )}
        </CardContent>
      </Card>
    </Box>
  );
};

export default ActivityDetails;
