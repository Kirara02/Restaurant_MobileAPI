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
    Public Class MsEmployeesController
        Inherits System.Web.Http.ApiController

        Private db As New LSKKasirAPIEntities

        ' GET: api/MsEmployees
        Function GetMsEmployees() As IQueryable(Of MsEmployee)
            Return db.MsEmployees
        End Function

        ' GET: api/MsEmployees/5
        <ResponseType(GetType(MsEmployee))>
        Function GetMsEmployee(ByVal id As Integer) As IHttpActionResult
            Dim msEmployee As MsEmployee = db.MsEmployees.Find(id)
            If IsNothing(msEmployee) Then
                Return NotFound()
            End If

            Return Ok(msEmployee)
        End Function

        ' PUT: api/MsEmployees/5
        <ResponseType(GetType(Void))>
        Function PutMsEmployee(ByVal id As Integer, ByVal msEmployee As MsEmployee) As IHttpActionResult
            If Not ModelState.IsValid Then
                Return BadRequest(ModelState)
            End If

            If Not id = msEmployee.id Then
                Return BadRequest()
            End If

            db.Entry(msEmployee).State = EntityState.Modified

            Try
                db.SaveChanges()
            Catch ex As DbUpdateConcurrencyException
                If Not (MsEmployeeExists(id)) Then
                    Return NotFound()
                Else
                    Throw
                End If
            End Try

            Return StatusCode(HttpStatusCode.NoContent)
        End Function

        ' POST: api/MsEmployees
        <ResponseType(GetType(MsEmployee))>
        Function PostMsEmployee(ByVal msEmployee As MsEmployee) As IHttpActionResult
            If Not ModelState.IsValid Then
                Return BadRequest(ModelState)
            End If

            db.MsEmployees.Add(msEmployee)
            db.SaveChanges()

            Return CreatedAtRoute("DefaultApi", New With {.id = msEmployee.id}, msEmployee)
        End Function

        ' POST: login
        <ResponseType(GetType(MsEmployee))>
        Function Login(ByVal msEmployee As MsEmployee) As IHttpActionResult
            If Not ModelState.IsValid Then
                Return BadRequest(ModelState)
            End If

            If (loginExists(msEmployee.email, msEmployee.password)) Then
                Dim status As New Dictionary(Of String, String)
                status.Add("status", "success")
                Return Ok(status)
            Else
                Dim status As New Dictionary(Of String, String)
                status.Add("status", "failed")
                Return Ok(status)
            End If
        End Function

        ' POST: register
        <ResponseType(GetType(MsEmployee))>
        Function Register(ByVal msEmployee As MsEmployee) As IHttpActionResult
            If Not ModelState.IsValid Then
                Return BadRequest(ModelState)
            End If

            If (registerExists(msEmployee.email)) Then
                Dim status As New Dictionary(Of String, String)
                status.Add("status", "failed")
                Return Ok(status)
            Else
                db.MsEmployees.Add(msEmployee)
                db.SaveChanges()
                Dim status As New Dictionary(Of String, String)
                status.Add("status", "success")
                Return Ok(status)
            End If
        End Function


        ' DELETE: api/MsEmployees/5
        <ResponseType(GetType(MsEmployee))>
        Function DeleteMsEmployee(ByVal id As Integer) As IHttpActionResult
            Dim msEmployee As MsEmployee = db.MsEmployees.Find(id)
            If IsNothing(msEmployee) Then
                Return NotFound()
            End If

            db.MsEmployees.Remove(msEmployee)
            db.SaveChanges()

            Return Ok(msEmployee)
        End Function

        Protected Overrides Sub Dispose(ByVal disposing As Boolean)
            If (disposing) Then
                db.Dispose()
            End If
            MyBase.Dispose(disposing)
        End Sub

        Private Function MsEmployeeExists(ByVal id As Integer) As Boolean
            Return db.MsEmployees.Count(Function(e) e.id = id) > 0
        End Function
        Private Function loginExists(ByVal email As String, ByVal pass As String) As Boolean
            Return db.MsEmployees.Count(Function(e) e.email = email And e.password = pass) > 0
        End Function
        Private Function registerExists(ByVal email As String) As Boolean
            Return db.MsEmployees.Count(Function(e) e.email = email) > 0
        End Function
    End Class
End Namespace