# Github Search
[Digital Innovation One](https://www.dio.me/en) challenge for sharing your GitHub portfolio. 

Built with Clean Architecture, using Repository pattern with a local datasource (for DataStore) and remote (for Retrofit). Hilt for depedency injection and unit tests for the ViewModel and PagingSource.

## App capabilities:
- Search all users from Github
- List all public repositories
- Save last user listed
- Quick share the repository
- Navigate to the repository on the browser

## Android Libraries/APIs used:
- Flow/Coroutines/LiveData
- DataStore
- ViewModel
- Paging3
- Dagger-Hilt
- OkHttp/Retrofit
- Mockk

[GitHub API](https://docs.github.com/en/rest) - Limitation: 60 searches per hour


https://user-images.githubusercontent.com/15269393/200148356-66c3d84d-1381-45f6-bf25-986cde69d6c5.mov

![image](https://user-images.githubusercontent.com/5827265/188474294-4472bcc0-24ee-4ccd-80a8-7cee0372e7fa.png)
