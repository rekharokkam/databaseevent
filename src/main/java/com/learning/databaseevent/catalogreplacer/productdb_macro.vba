Sub create_db()
    Dim LastRow As Long
    LastRow = Cells(Rows.Count, 1).End(xlUp).Row

    Dim columnHeaders(6) As String

    columnHeaders(1) = "prId"
    columnHeaders(2) = "name"
    columnHeaders(3) = "tcin"
    columnHeaders(4) = "dpci"
    columnHeaders(5) = "upc"
    columnHeaders(6) = "model"

    Dim num As Integer

    num = FreeFile()
    Open "/Users/z00465g/Documents/Rekha/personal_workspace/database-event/src/main/resources/productdb.json" For Output As #num

    Print #num, "["

    Dim i As Long
    For i = 2 To LastRow

        Print #num, "{"

        Dim j As Long
        For j = 1 To 6
             If j < 6 Then
                Print #num, Chr(34) & columnHeaders(j) & Chr(34) & ":" & Chr(34); Application.Trim(Cells(i, j).Value) & Chr(34) & ","
            Else
                Print #num, Chr(34) & columnHeaders(j) & Chr(34) & ":" & Chr(34); Application.Trim(Cells(i, j).Value) & Chr(34)
            End If
        Next j

        If i < LastRow Then
            Print #num, "},"
        Else
            Print #num, "}"
        End If

    Next i

    Print #num, "]"

    Close #num

End Sub

