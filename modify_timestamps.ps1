# ================================================================
#  WINDOWS DATE MODIFIED TIMESTAMP MODIFIER
#  Modifies "Date Modified" (LastWriteTime) shown in File Explorer
# ================================================================
#
#  QUICK START
#  -----------
#  1. Place this script in the folder where your target files are
#     (optional but enables short relative names instead of full paths)
#  2. Right-click > "Run with PowerShell"  OR  run via terminal:
#       powershell -ExecutionPolicy Bypass -File modify_timestamps.ps1
#  3. Choose a mode from the menu
#
# ================================================================
#
#  MODES EXPLAINED
#  ---------------
#
#  [1] Load from TXT files
#      --------------------
#      Reads two files from the script's directory:
#        - dates_and_times.txt   : one datetime per line
#        - targets_path_list.txt : one file/folder path per line
#
#      Pairing is LINE-BY-LINE:
#        Line 1 of dates  -->  Line 1 of targets
#        Line 2 of dates  -->  Line 2 of targets
#        ...and so on
#
#      BOTH FILES MUST HAVE THE SAME NUMBER OF LINES.
#      Blank lines are skipped automatically.
#
#      You can also provide custom file paths at the prompt
#      instead of using the default filenames.
#
#  [2] Manual entry
#      -------------
#      Type each datetime + path pair directly in the terminal.
#      Useful for quick one-off changes without creating any files.
#      Type 'done' when you have entered all entries.
#
#  [3] Mixed mode
#      -----------
#      Load target paths from targets_path_list.txt,
#      then type the datetime for each target manually.
#      Good when you have many targets but want to assign
#      dates interactively one by one.
#
#  [4] Preview timestamps
#      -------------------
#      Enter any paths and see their current "Date Modified"
#      value WITHOUT making any changes. Use this to verify
#      before or after applying modifications.
#
# ================================================================
#
#  HOW TO WRITE dates_and_times.txt
#  ----------------------------------
#  - One datetime per line
#  - Do NOT add headers or comments inside the file
#  - Blank lines are ignored
#
#  Accepted formats:
#    yyyy-MM-dd HH:mm:ss   -->  2024-01-15 09:30:00   (full datetime)
#    yyyy-MM-dd HH:mm      -->  2024-01-15 09:30       (no seconds)
#    yyyy-MM-dd            -->  2024-01-15              (date only, time = 00:00:00)
#    dd-MM-yyyy HH:mm:ss   -->  15-01-2024 09:30:00
#    dd/MM/yyyy HH:mm:ss   -->  15/01/2024 09:30:00
#
#  Example dates_and_times.txt:
#    2024-01-15 09:30:00
#    2024-03-22 14:00:00
#    2025-07-04 00:00:00
#    2023-11-01 18:45:00
#
# ================================================================
#
#  HOW TO WRITE targets_path_list.txt
#  ------------------------------------
#  - One file or folder path per line
#  - Do NOT add headers or comments inside the file
#  - Blank lines are ignored
#  - Line count MUST match dates_and_times.txt exactly
#
#  Two types of paths accepted:
#
#  (A) RELATIVE NAME — just the filename or folder name
#      Use this when the target is in the SAME folder as the script
#      Examples:
#        report.docx
#        notes.txt
#        my_project_folder
#        archive.zip
#
#  (B) ABSOLUTE PATH — full path from drive root
#      Use this for files/folders anywhere on the system
#      Examples:
#        C:\Users\Akshay\Documents\project
#        C:\Users\Akshay\Desktop\image.png
#        D:\Backups\old_files
#
#  You can MIX relative and absolute paths in the same file:
#    report.docx
#    C:\Users\Akshay\Desktop\resume.pdf
#    subfolder_name
#    D:\archive\logs
#
#  Example targets_path_list.txt (4 lines = must have 4 dates too):
#    report.docx
#    C:\Users\Akshay\Documents\project_folder
#    notes.txt
#    old_backup
#
# ================================================================
#
#  FOLDER BEHAVIOR
#  ---------------
#  - The script sets the timestamp on the FOLDER ITSELF only
#  - Files inside the folder are NOT affected (not recursive)
#  - If you later add/modify files inside a folder, Windows may
#    update the folder's timestamp automatically — this is normal
#    OS behavior and cannot be prevented by this script
#
# ================================================================
#
#  PERMISSIONS
#  -----------
#  - For files/folders in your user profile: no elevation needed
#  - For system folders or other users' files: run as Administrator
#    Right-click PowerShell > "Run as Administrator", then run:
#      Set-Location "path\to\script\folder"
#      .\modify_timestamps.ps1
#
# ================================================================
#
#  EXECUTION POLICY (if script is blocked)
#  ----------------------------------------
#  Windows may block unsigned scripts. Fix with either:
#    powershell -ExecutionPolicy Bypass -File modify_timestamps.ps1
#  Or set permanently for current user:
#    Set-ExecutionPolicy -Scope CurrentUser RemoteSigned
#
# ================================================================

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition

function Show-Header {
    Clear-Host
    Write-Host "================================================" -ForegroundColor Cyan
    Write-Host "   Windows Date Modified Timestamp Modifier"     -ForegroundColor Cyan
    Write-Host "================================================" -ForegroundColor Cyan
    Write-Host ""
}

function Pause-Menu {
    Write-Host ""
    Write-Host "Press any key to return to menu..." -ForegroundColor DarkGray
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
}

# ----------------------------------------------------------------
# APPLY LOGIC
# Iterates mappings, resolves paths, sets LastWriteTime
# ----------------------------------------------------------------
function Apply-Timestamps ($mappings) {
    if ($mappings.Count -eq 0) {
        Write-Warning "No mappings to apply."
        return
    }

    $success = 0; $fail = 0

    Write-Host ""
    foreach ($entry in $mappings) {
        $dt     = $entry.DateTime
        $target = $entry.Target

        # Relative name → resolve against script directory
        if (-not [System.IO.Path]::IsPathRooted($target)) {
            $target = Join-Path $scriptDir $target
        }

        if (-not (Test-Path -LiteralPath $target)) {
            Write-Warning "NOT FOUND : $target"
            $fail++
            continue
        }

        try {
            (Get-Item -LiteralPath $target -Force).LastWriteTime = $dt
            Write-Host "  OK  [$($dt.ToString('yyyy-MM-dd HH:mm:ss'))]  $target" -ForegroundColor Green
            $success++
        } catch {
            Write-Warning "FAILED : $target — $($_.Exception.Message)"
            $fail++
        }
    }

    Write-Host ""
    Write-Host "Done. Success: $success | Errors: $fail" -ForegroundColor Cyan
}

# ----------------------------------------------------------------
# DATETIME PARSER
# Tries multiple common formats; returns $null if none match
# ----------------------------------------------------------------
function Parse-DateTime ($raw) {
    $dt = $null
    $formats = @(
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd HH:mm",
        "yyyy-MM-dd",
        "dd-MM-yyyy HH:mm:ss",
        "dd/MM/yyyy HH:mm:ss"
    )
    foreach ($fmt in $formats) {
        if ([datetime]::TryParseExact($raw.Trim(), $fmt, $null, 'None', [ref]$dt)) {
            return $dt
        }
    }
    return $null
}

# ================================================================
#  MODE 1 — Load from TXT files (line-by-line pairing)
# ================================================================
function Mode-FromFiles {
    Show-Header
    Write-Host "  MODE: Load from TXT files" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "  Default files expected in script directory:"
    Write-Host "    dates_and_times.txt     — one datetime per line"
    Write-Host "    targets_path_list.txt   — one path or name per line"
    Write-Host "  Line count in both files must be equal."
    Write-Host ""

    $dtFile  = Join-Path $scriptDir "dates_and_times.txt"
    $tgtFile = Join-Path $scriptDir "targets_path_list.txt"

    # Allow user to override default file paths
    $custom = Read-Host "  Press Enter for default dates file, or type custom path"
    if ($custom.Trim() -ne "") { $dtFile = $custom.Trim() }

    $custom = Read-Host "  Press Enter for default targets file, or type custom path"
    if ($custom.Trim() -ne "") { $tgtFile = $custom.Trim() }

    foreach ($f in @($dtFile, $tgtFile)) {
        if (-not (Test-Path $f)) {
            Write-Warning "File not found: $f"
            Pause-Menu; return
        }
    }

    # Read and filter blank lines
    $dates   = Get-Content $dtFile   | Where-Object { $_.Trim() -ne "" }
    $targets = Get-Content $tgtFile  | Where-Object { $_.Trim() -ne "" }

    if ($dates.Count -ne $targets.Count) {
        Write-Warning "Line count mismatch — dates: $($dates.Count), targets: $($targets.Count)"
        Write-Host "  Both files must have the same number of non-blank lines." -ForegroundColor Red
        Pause-Menu; return
    }

    $mappings = @()
    for ($i = 0; $i -lt $dates.Count; $i++) {
        $dt = Parse-DateTime $dates[$i]
        if ($null -eq $dt) {
            Write-Warning "Invalid datetime on line $($i+1): '$($dates[$i])' — skipped"
            continue
        }
        $mappings += [PSCustomObject]@{ DateTime = $dt; Target = $targets[$i].Trim() }
    }

    Write-Host ""
    Write-Host "  Parsed $($mappings.Count) mapping(s). Applying..." -ForegroundColor Cyan
    Apply-Timestamps $mappings
    Pause-Menu
}

# ================================================================
#  MODE 2 — Manual entry at runtime
# ================================================================
function Mode-Manual {
    Show-Header
    Write-Host "  MODE: Manual entry" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "  Enter datetime and path for each target one by one."
    Write-Host "  Path: full absolute path  OR  just name (resolved from script dir)"
    Write-Host "  Type 'done' as datetime when finished."
    Write-Host ""

    $mappings = @()

    while ($true) {
        $rawDt = Read-Host "  Datetime (e.g. 2024-06-01 14:30:00) or 'done'"
        if ($rawDt.Trim() -eq "done") { break }

        $dt = Parse-DateTime $rawDt
        if ($null -eq $dt) {
            Write-Warning "  Invalid format. Accepted: yyyy-MM-dd HH:mm:ss or yyyy-MM-dd"
            continue
        }

        $rawPath = Read-Host "  Target path or filename/foldername"
        if ($rawPath.Trim() -eq "") {
            Write-Warning "  Empty path — skipped."
            continue
        }

        $mappings += [PSCustomObject]@{ DateTime = $dt; Target = $rawPath.Trim() }
        Write-Host "  Added. Total queued: $($mappings.Count)" -ForegroundColor DarkGreen
        Write-Host ""
    }

    if ($mappings.Count -eq 0) {
        Write-Warning "No entries added."
        Pause-Menu; return
    }

    Write-Host ""
    Write-Host "  $($mappings.Count) mapping(s) queued. Applying..." -ForegroundColor Cyan
    Apply-Timestamps $mappings
    Pause-Menu
}

# ================================================================
#  MODE 3 — Targets from file, dates entered manually per target
# ================================================================
function Mode-Mixed {
    Show-Header
    Write-Host "  MODE: Targets from file, dates entered manually" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "  Loads target list from file, then prompts for each target's datetime."
    Write-Host ""

    $tgtFile = Join-Path $scriptDir "targets_path_list.txt"
    $custom  = Read-Host "  Press Enter for default targets_path_list.txt, or type path"
    if ($custom.Trim() -ne "") { $tgtFile = $custom.Trim() }

    if (-not (Test-Path $tgtFile)) {
        Write-Warning "File not found: $tgtFile"
        Pause-Menu; return
    }

    $targets = Get-Content $tgtFile | Where-Object { $_.Trim() -ne "" }
    Write-Host "  Loaded $($targets.Count) target(s)." -ForegroundColor DarkGreen
    Write-Host ""

    $mappings = @()
    foreach ($t in $targets) {
        Write-Host "  Target: $t" -ForegroundColor White
        $rawDt = Read-Host "  Datetime"
        $dt = Parse-DateTime $rawDt
        if ($null -eq $dt) {
            Write-Warning "  Invalid datetime — skipped: $t"
            continue
        }
        $mappings += [PSCustomObject]@{ DateTime = $dt; Target = $t.Trim() }
        Write-Host ""
    }

    if ($mappings.Count -eq 0) {
        Write-Warning "No valid entries."
        Pause-Menu; return
    }

    Write-Host "  $($mappings.Count) mapping(s) queued. Applying..." -ForegroundColor Cyan
    Apply-Timestamps $mappings
    Pause-Menu
}

# ================================================================
#  MODE 4 — Preview current timestamps (read-only, no changes)
# ================================================================
function Mode-Preview {
    Show-Header
    Write-Host "  MODE: Preview current timestamps (read-only)" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "  Enter paths to inspect. Type 'done' when finished."
    Write-Host ""

    $paths = @()
    while ($true) {
        $p = Read-Host "  Path or name"
        if ($p.Trim() -eq "done") { break }
        if ($p.Trim() -ne "") { $paths += $p.Trim() }
    }

    Write-Host ""
    foreach ($p in $paths) {
        if (-not [System.IO.Path]::IsPathRooted($p)) {
            $p = Join-Path $scriptDir $p
        }
        if (Test-Path -LiteralPath $p) {
            $item = Get-Item -LiteralPath $p -Force
            Write-Host ("  {0,-45} Modified: {1}" -f $item.Name, $item.LastWriteTime.ToString("yyyy-MM-dd HH:mm:ss")) -ForegroundColor White
        } else {
            Write-Warning "  NOT FOUND: $p"
        }
    }

    Pause-Menu
}

# ================================================================
#  MAIN MENU LOOP
# ================================================================
while ($true) {
    Show-Header
    Write-Host "  [1]  Load from TXT files  (dates_and_times.txt + targets_path_list.txt)"
    Write-Host "  [2]  Manual entry          (type paths and datetimes at runtime)"
    Write-Host "  [3]  Mixed                 (targets from file, dates typed manually)"
    Write-Host "  [4]  Preview timestamps    (read-only, no changes made)"
    Write-Host "  [0]  Exit"
    Write-Host ""
    $choice = Read-Host "  Select"

    switch ($choice.Trim()) {
        "1" { Mode-FromFiles }
        "2" { Mode-Manual    }
        "3" { Mode-Mixed     }
        "4" { Mode-Preview   }
        "0" { Clear-Host; exit }
        default { Write-Warning "Invalid option."; Start-Sleep 1 }
    }
}
