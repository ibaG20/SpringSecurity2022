#Ordem:

* Começar fazendo CRUD de user

* depois colocar a segurança nos packeges:
  
  V model
   V. UserModel
   V. MainUser
   V. Role
   
  V repository
   V. RoleRepository
   V. UserRepository
   
  V service
   V. RoleService
   V. UserService
   V. UserDetailsServiceImpl
  
  V enums 
   V. RoleList
   
  V dtos.auth
   V. AuthenticationDTO
   V. NewUser
   V. TokenDTO
   
  V jwt
   V. AuthenticationService
   V. JwtEntryPoint
   
  V filters
   V. AuthenticationFilter
   
  - config
   . SecurityConfig
   
  - Controller
   . AuthenticationController