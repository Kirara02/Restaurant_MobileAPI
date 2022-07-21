Imports System
Imports System.Collections.Generic
Imports System.Linq
Imports System.Web.Http

Public Module WebApiConfig
    Public Sub Register(ByVal config As HttpConfiguration)
        ' Web API configuration and services

        ' Web API routes
        config.MapHttpAttributeRoutes()

        config.Routes.MapHttpRoute(
            name:="DefaultApi",
            routeTemplate:="api/{controller}/{id}",
            defaults:=New With {.id = RouteParameter.Optional}
        )

        config.Routes.MapHttpRoute(
            name:="Login",
            routeTemplate:="login",
            defaults:=New With {.controller = "MsEmployees", .action = "Login"}
        )
        config.Routes.MapHttpRoute(
            name:="Register",
            routeTemplate:="register",
            defaults:=New With {.controller = "MsEmployees", .action = "Register"}
        )
        config.Routes.MapHttpRoute(
            name:="Gmenu",
            routeTemplate:="menu",
            defaults:=New With {.controller = "MsMenus", .action = "Gmenu"}
        )
        config.Routes.MapHttpRoute(
            name:="Pmenu",
            routeTemplate:="menu",
            defaults:=New With {.controller = "MsMenus", .action = "Pmenu"}
        )
        config.Routes.MapHttpRoute(
            name:="Edit",
            routeTemplate:="menu/{id}",
            defaults:=New With {.controller = "MsMenus", .action = "Edit", .id = RouteParameter.Optional}
        )
        config.Routes.MapHttpRoute(
            name:="Delete",
            routeTemplate:="menu/{id}",
            defaults:=New With {.controller = "MsMenus", .action = "Delete", .id = RouteParameter.Optional}
        )
    End Sub
End Module
