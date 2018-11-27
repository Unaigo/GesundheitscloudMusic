# GesundheitscloudMusic
This app is a simple search application of Music Itunes api , to get different songs and check and filter information where 
user can play these searched music.

I separated this project on different folders : Logic ,Models ,Presenters , ServerConnection and Views

LOGIC
In logic I added two classes , one to manage(factory server model to client model) and keep the data in memory
And also the class that contains the logic of request to the API.

MODELS
I created two folders, one for ServerModels where we parse the Json information, and other ClientModels , that will be the 
models that we use in the client side , the factory is done on LOGIC.

PRESENTERS
I only add one presenter , to manage the view and logic of the Search List, there we can see all the logic on search and 
filters

SERVERCONNECTION
I add and interface class , that implements retrofit library where I define the calls of the server

VIEWS 
Here are all the view of the app and also the adapter of recyclerView.

I spend 8 hours to do it this project , the double that is expected , but I take care about design and I modified a little bit
the request of the example filtering with a query to get only music response, and I also customize some views with drawables...

If you have any doubt , please let me know
