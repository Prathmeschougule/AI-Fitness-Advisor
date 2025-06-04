import { Box, Button, Typography } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "react-oauth2-code-pkce";
import { useDispatch } from "react-redux";
import {
  BrowserRouter as Router,
  Navigate,
  Route,
  Routes,
  useLocation,
} from "react-router";
import { setCredential } from "./store/authSlice";
import ActivityForm from "./Component/ActivityForm";
import ActivityList from "./Component/ActivityList";
import ActivityDetails from "./Component/ActivityDetails";

const ActivitiesPage = () => {
  return (
    <Box sx={{ p: 2, border: "1px dashed grey" }}>
      <ActivityForm onActivitiesAdded={() => window.location.reload()} />
      <ActivityList />
    </Box>
  );
};

function App() {
  const { token, tokenData, logIn, logOut, isAuthenticated } =
    useContext(AuthContext);
  const dispatch = useDispatch();
  const [authReady, setAuthReady] = useState(false);

  useEffect(() => {
    if (token) {
      dispatch(setCredential({ token, user: tokenData }));
      setAuthReady(true);
    }
  }, [token, tokenData, dispatch]);

  return (
    <>
      <Router>
        {!token ? (
          <Button
            variant="contained"
            color="secondary"
            onClick={() => {
              logIn();
            }}
          >
            {" "}
            Login{" "}
          </Button>
        ) : (
          <div>
            {/* <pre>
            {JSON.stringify(tokenData,null,2)}
            {JSON.stringify(token,null,2)}
          </pre> */}
            <div
              style={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
                margin: 24,
              }}
            >
              <Typography level="h1">Fitness AI Assistance</Typography>
              <Button
                variant="contained"
                color="secondary"
                onClick={() => {
                  logOut();
                }}
              >
                Logout
              </Button>
            </div>

            <Box component="section" sx={{ p: 2, border: "1px dashed grey" }}>
              <Routes>
                <Route path="/activities" element={<ActivitiesPage />} />
                <Route path="/activities/:id" element={<ActivityDetails />} />
                <Route
                  path="/"
                  element={
                    token ? (
                      <Navigate to={"/activities"} replace />
                    ) : (
                      <div> WelCome Please Login. </div>
                    )
                  }
                />
              </Routes>
            </Box>
          </div>
        )}
      </Router>
    </>
  );
}

export default App;
