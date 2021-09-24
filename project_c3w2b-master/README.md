# Jet Fighter

## What the game is 

This Game is called **Jet Fighter** and will
consist of two jets that shoot at each other.  

Each time you shoot the other jet you get a point.  

One game lasts 1 or 2 minutes.

Possible features to include
- obstacles 
- asteroids which collide with a jet and deducts points
- special gun updates
- special jet updates

A player will be a jet and every jet has a set of guns. A player
can pick which gun they want to use. A Jet will be able to obtain a new gun 
by driving into a specific object or by getting shot by/shooting the other player
multiple times in a row.

## Who will use the game
This game is for any and all people who want to play.

## Why I chose this project
I chose to make a game for this project because  
I am interested in game development.

## User Stories 
- As a user, I want to be able to add a gun to a jet
- As a user, I want to be able to access the amount 
  of points I have
- As a user, I want to be able to shoot a projectile
- As a user, I want to be able to choose the gun I want to use.

- As a user, I want to be able to save a state of a game
- As a user, I want to be able to load a state of a game

## Phase 4: Task:2
I have chosen to make the chooseGun(int i) in the Jet class more robust
This method throws an exception if someone tries to choose
an invalid index. This is tested in the Jet test class by the methods
public void testChooseGunIndexInBoundsExpectNoExceptionThrown() and
public void testChooseGunIndexOutOfBoundsExpectExceptionThrown()

## Phase 4: Task:3
Changes I would make if I had more time:
- I would change all the Lists to Sets as there should be no duplicates and there is no need to keep order
- I would make a Jet have a field that is a Set of Projectiles and therefore JetFighterGame would not need to 
have the two fields jet1Projectiles and jet2Projectiles (which are lists of projectiles)
- I would also implement the observer pattern to ScorePanel and GunsPanel, so ScorePanel and GunsPanel
would implement Observer and JetFighterGame would extend Observable
therefore we can remove the association between JetFighterGame and these panels
