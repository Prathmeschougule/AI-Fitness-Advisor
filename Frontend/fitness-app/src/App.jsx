import { Button } from "@mui/material"
import { useContext, useEffect, useState } from "react"
import { AuthContext } from "react-oauth2-code-pkce"
import { useDispatch } from "react-redux"
import { BrowserRouter as Router, Navigate,Route,Routes,useLocation} from "react-router"
import { setCredential } from "./store/authSlice"

function App() {
  const {token , tokenData, logIn ,logOut,isAuthenticated} = useContext( AuthContext);
  const dispatch = useDispatch();
  const [authReady,setAuthReady] = useState(false);
  useEffect(()=>{
    if(token){
      dispatch(setCredential({token,user:tokenData}))
      setAuthReady(true);
    }
  } ,[token,dispatch,tokenData] )
  return (    
    <>
    <Router>
      {!token ?(
      <Button variant="contained" color="secondary"
        onClick={()=>{
          logIn();
        }}
      > Login </Button>
      ):(
        <div>
          <pre>
            {JSON.stringify(tokenData,null,2)}
          </pre>
        </div>
      )}
    </Router>
    </>
  )
}

export default App
