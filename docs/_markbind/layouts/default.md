<head-bottom>
  <link rel="stylesheet" href="{{baseUrl}}/stylesheets/main.css">
</head-bottom>

<header sticky>
  <navbar type="dark">
    <a slot="brand" href="{{baseUrl}}/index.html" title="Home" class="navbar-brand">B2B4U</a>
    <li><a href="{{baseUrl}}/index.html" class="nav-link">Home</a></li>
    <li><a href="{{baseUrl}}/UserGuide.html" class="nav-link">User Guide</a></li>
    <li><a href="{{baseUrl}}/developer-guide/index.html" class="nav-link">Developer Guide</a></li>
    <li><a href="{{baseUrl}}/AboutUs.html" class="nav-link">About Us</a></li>
    <li><a href="https://github.com/AY2526S2-CS2103T-T08-1/tp" target="_blank" class="nav-link"><md>:fab-github:</md></a>
    </li>
    <li slot="right">
      <form class="navbar-form">
        <searchbar :data="searchData" placeholder="Search" :on-hit="searchCallback" menu-align-right></searchbar>
      </form>
    </li>
  </navbar>
</header>

<div id="flex-body">
  <nav id="site-nav">
    <div class="site-nav-top">
      <div class="fw-bold mb-2" style="font-size: 1.25rem;">Site Map</div>
    </div>
    <div class="nav-component slim-scroll">
      <site-nav>
* [Home]({{ baseUrl }}/index.html)
* [User Guide]({{ baseUrl }}/UserGuide.html) :expanded:
  * [Viewing Help]({{ baseUrl }}/user-guide/view-help.html)
  * [Adding a Contact]({{ baseUrl }}/user-guide/add-contact.html)
  * [Editing a Contact]({{ baseUrl }}/user-guide/edit-contact.html)
  * [Deleting a Contact]({{ baseUrl }}/user-guide/delete-contact.html)
  * [Clearing Contacts]({{ baseUrl }}/user-guide/clear-contacts.html)
  * [Listing Contacts]({{ baseUrl }}/user-guide/list-contacts.html)
  * [Finding Contacts]({{ baseUrl }}/user-guide/find-contacts.html)
  * [Sorting Contacts]({{ baseUrl }}/user-guide/sort-contacts.html)
  * [Managing Notes]({{ baseUrl }}/user-guide/notes.html)
  * [Undoing a Command]({{ baseUrl }}/user-guide/undo-command.html)
  * [Redoing a Command]({{ baseUrl }}/user-guide/redo-command.html)
  * [Viewing Contact/File Details]({{ baseUrl }}/user-guide/view.html)
  * [Closing the Contact View]({{ baseUrl }}/user-guide/close-view-contact.html)
  * [Managing Files]({{ baseUrl }}/user-guide/file.html)
  * [Changing the Theme]({{ baseUrl }}/user-guide/set-theme.html)
  * [Exiting the Program]({{ baseUrl }}/user-guide/exit-program.html)
* [Developer Guide]({{ baseUrl }}/developer-guide/index.html) :expanded:
  * [Setting Up & Getting Started]({{ baseUrl }}/developer-guide/setting-up-getting-started.html)
  * [Design]({{ baseUrl }}/developer-guide/design.html)
  * [Implementation]({{ baseUrl }}/developer-guide/implementation.html)
  * [Documentation]({{ baseUrl }}/developer-guide/documentation.html)
  * [Logging]({{ baseUrl }}/developer-guide/logging.html)
  * [Testing]({{ baseUrl }}/developer-guide/testing.html)
  * [Configuration]({{ baseUrl }}/developer-guide/configuration.html)
  * [DevOps]({{ baseUrl }}/developer-guide/devops.html)
  * [Appendix: Requirements]({{ baseUrl }}/developer-guide/appendix-requirements.html)
  * [Appendix: Instructions for Manual Testing]({{ baseUrl }}/developer-guide/appendix-manual-testing.html)
* [About Us]({{ baseUrl }}/AboutUs.html)
      </site-nav>
    </div>
  </nav>
  <div id="content-wrapper">
    {{ content }}
  </div>
  <nav id="page-nav">
    <div class="nav-component slim-scroll">
      <page-nav />
    </div>
  </nav>
  <scroll-top-button></scroll-top-button>
</div>

<footer>
  <!-- Support MarkBind by including a link to us on your landing page! -->
  <div class="text-center">
    <small>[<md>**Powered by**</md> <img src="https://markbind.org/favicon.ico" width="30"> {{MarkBind}}, generated on {{timestamp}}]</small>
  </div>
</footer>
