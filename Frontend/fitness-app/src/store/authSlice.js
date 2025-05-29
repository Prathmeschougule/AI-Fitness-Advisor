import js from '@eslint/js';
import { createSlice } from '@reduxjs/toolkit'

const authSlice = createSlice({
  name: 'auth',
  initialState :{
    user:JSON.parse(localStorage.getItem('user')) || null,
    token: localStorage.getItem('token') || null,
    userId: localStorage.getItem('userId') || null
  },
  reducers: {
    setCredential:(state,action) => {
     state.user = action.payload.user;
     state.token = action.payload.token;
     state.userId = action.payload.userId.sub;

     localStorage.setItem('token',action.payload.token);
     localStorage.setItem('user',JSON.stringify(action.payload.user));
     localStorage.setItem('userId',action.payload.user.sub);
    },
    logOut:(state)=> {
        state.user = null;
        state.token = null;
        state.userId = null;
        
        localStorage.removeItem('user');
        localStorage.removeItem('token');
        localStorage.removeItem('userId');
   
    },
  },
})

export const { setCredential,logout } = authSlice.actions;
export default authSlice.reducer;