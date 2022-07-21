Imports System.Data
Imports System.Data.Entity
Imports System.Data.Entity.Infrastructure
Imports System.Linq
Imports System.Net
Imports System.Net.Http
Imports System.Web.Http
Imports System.Web.Http.Description
Imports LKSKasirAPI

Namespace Controllers
    Public Class MsMenusController
        Inherits System.Web.Http.ApiController

        Private db As New LSKKasirAPIEntities

        ' GET: api/MsMenus
        Function GetMsMenus() As IQueryable(Of MsMenu)
            Return db.MsMenus
        End Function

        ' GET: menu
        <HttpGet>
        <Route("menu")>
        Function GMenu() As IQueryable(Of MsMenu)
            Return db.MsMenus
        End Function


        ' GET: api/MsMenus/5
        <ResponseType(GetType(MsMenu))>
        Function GetMsMenu(ByVal id As Integer) As IHttpActionResult
            Dim msMenu As MsMenu = db.MsMenus.Find(id)
            If IsNothing(msMenu) Then
                Return NotFound()
            End If

            Return Ok(msMenu)
        End Function

        ' PUT: api/MsMenus/5
        <ResponseType(GetType(Void))>
        Function PutMsMenu(ByVal id As Integer, ByVal msMenu As MsMenu) As IHttpActionResult
            If Not ModelState.IsValid Then
                Return BadRequest(ModelState)
            End If

            If Not id = msMenu.id Then
                Return BadRequest()
            End If

            db.Entry(msMenu).State = EntityState.Modified

            Try
                db.SaveChanges()
            Catch ex As DbUpdateConcurrencyException
                If Not (MsMenuExists(id)) Then
                    Return NotFound()
                Else
                    Throw
                End If
            End Try

            Return StatusCode(HttpStatusCode.NoContent)
        End Function

        ' PUT: menu/3
        <ResponseType(GetType(Void))>
        <HttpPut>
        <Route("menu/{id:int}")>
        Function Edit(ByVal id As Integer, ByVal msMenu As MsMenu) As IHttpActionResult
            If Not ModelState.IsValid Then
                Return BadRequest(ModelState)
            End If

            If Not id = msMenu.id Then
                Return BadRequest()
            End If

            db.Entry(msMenu).State = EntityState.Modified

            Try
                db.SaveChanges()
            Catch ex As DbUpdateConcurrencyException
                If Not (MsMenuExists(id)) Then
                    Return NotFound()
                Else
                    Throw
                End If
            End Try

            Return StatusCode(HttpStatusCode.NoContent)
        End Function

        ' POST: api/MsMenus
        <ResponseType(GetType(MsMenu))>
        Function PostMsMenu(ByVal msMenu As MsMenu) As IHttpActionResult
            If Not ModelState.IsValid Then
                Return BadRequest(ModelState)
            End If

            db.MsMenus.Add(msMenu)
            db.SaveChanges()

            Return CreatedAtRoute("DefaultApi", New With {.id = msMenu.id}, msMenu)
        End Function


        ' POST: menu
        <ResponseType(GetType(MsMenu))>
        <HttpPost>
        <Route("menu")>
        Function Pmenu(ByVal msMenu As MsMenu) As IHttpActionResult
            If Not ModelState.IsValid Then
                Return BadRequest(ModelState)
            End If

            db.MsMenus.Add(msMenu)
            db.SaveChanges()

            Return Ok(msMenu)
        End Function

        ' DELETE: api/MsMenus/5
        <ResponseType(GetType(MsMenu))>
        Function DeleteMsMenu(ByVal id As Integer) As IHttpActionResult
            Dim msMenu As MsMenu = db.MsMenus.Find(id)
            If IsNothing(msMenu) Then
                Return NotFound()
            End If

            db.MsMenus.Remove(msMenu)
            db.SaveChanges()

            Return Ok(msMenu)
        End Function

        ' DELETE: menu/2
        <ResponseType(GetType(MsMenu))>
        <HttpDelete>
        <Route("menu/{id:int}")>
        Function Delete(ByVal id As Integer) As IHttpActionResult
            Dim msMenu As MsMenu = db.MsMenus.Find(id)
            If IsNothing(msMenu) Then
                Return NotFound()
            End If

            db.MsMenus.Remove(msMenu)
            db.SaveChanges()

            Return Ok(msMenu)
        End Function

        Protected Overrides Sub Dispose(ByVal disposing As Boolean)
            If (disposing) Then
                db.Dispose()
            End If
            MyBase.Dispose(disposing)
        End Sub

        Private Function MsMenuExists(ByVal id As Integer) As Boolean
            Return db.MsMenus.Count(Function(e) e.id = id) > 0
        End Function
    End Class
End Namespace