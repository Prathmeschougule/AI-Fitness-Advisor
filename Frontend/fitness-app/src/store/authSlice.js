import { createSlice } from '@reduxjs/toolkit';

const authSlice = createSlice({
  name: 'auth',
  initialState: {
    user: (() => {
      try {
        const user = localStorage.getItem('user');
        return user ? JSON.parse(user) : null;
      } catch (error) {
        console.error('Error parsing user from localStorage:', error);
        return null;
      }
    })(),
    token: localStorage.getItem('token') || null,
    userId: localStorage.getItem('userId') || null,
  },
  reducers: {
    setCredential: (state, action) => {
      state.user = action.payload.user;
      state.token = action.payload.token;
      state.userId = action.payload.user?.sub || null; // Access sub from user

      localStorage.setItem('token', action.payload.token);
      localStorage.setItem('user', JSON.stringify(action.payload.user));
      localStorage.setItem('userId', action.payload.user?.sub || '');
    },
    logOut: (state) => {
      state.user = null;
      state.token = null;
      state.userId = null;

      localStorage.removeItem('user');
      localStorage.removeItem('token');
      localStorage.removeItem('userId');
    },
  },
});

export const { setCredential, logOut } = authSlice.actions; // Fixed typo: logout -> logOut
export default authSlice.reducer;