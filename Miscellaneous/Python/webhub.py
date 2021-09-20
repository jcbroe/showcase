import webbrowser


MENU_OPTIONS = ("0: Exit  \n"
                "1: Google \n"
                "2. Youtube \n"
                "3: Enter a website")
POST_OPTIONS = ("0. .com    \n"
                "1. .gov    \n"
                "2. .org    \n"
                "3. .net")
PREFIX = "https://www."


def open_chrome(site):
    webbrowser.open_new(site)


def show_options():
    print(MENU_OPTIONS)


def show_postfix():
    print(POST_OPTIONS)


def get_selection():
    selection = input("Choose: ")
    while not selection:
        selection = input("Try again: ")
    return selection


def get_custom(postfix):
    custom_site = input("Enter the site name: ")
    while not custom_site:
        custom_site = input("Try again: ")
    custom_site = PREFIX + custom_site + postfix
    return custom_site


def post_selection_process(postfix_selection):
    while True:
        if postfix_selection == "0":
            postfix = ".com"
            break
        elif postfix_selection == "1":
            postfix = ".gov"
            break
        elif postfix_selection == "2":
            postfix = ".org"
            break
        elif postfix_selection == "3":
            postfix = ".net"
            break
        else:
            print("invalid. Try again: ")
    return postfix


def postfix_processing():
    show_postfix()
    postfix_selection = get_selection()
    postfix = post_selection_process(postfix_selection)
    return postfix


def selection_processing():
    while True:
        show_options()
        site_select = get_selection()
        print(site_select)
        if site_select == "0":
            print("exiting...")
            break
        elif site_select == "1":
            site = "https://www.google.com"
            open_chrome(site)
        elif site_select == "2":
            site = "https://www.youtube.com"
            open_chrome(site)
        elif site_select == "3":
            post_selection = postfix_processing()
            site = get_custom(post_selection)
            open_chrome(site)
        else:
            print("invalid input. try again: ")


if __name__ == "__main__":
    selection_processing()

