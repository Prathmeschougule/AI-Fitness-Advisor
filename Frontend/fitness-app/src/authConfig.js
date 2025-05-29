
export const authConfig = {
  clientId: 'oauth2-pkce-client',
  authorizationEndpoint: 'http://localhost:8181/realms/fitness-oautha2/protocol/openid-connect/auth://myAuthProvider.com/auth',
  tokenEndpoint: 'https://http://localhost:8181/realms/fitness-oautha2/protocol/openid-connect/token.com/token',
  redirectUri: 'http://localhost:5173/',
  scope: 'openid profile Email offline_access' ,
  onRefreshTokenExpire: (event) => event.logIn(),
}